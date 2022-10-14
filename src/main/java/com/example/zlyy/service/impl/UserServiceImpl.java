package com.example.zlyy.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zlyy.common.R;
import com.example.zlyy.pojo.Admin;
import com.example.zlyy.pojo.dto.UserDTO;
import com.example.zlyy.pojo.bo.WXAuth;
import com.example.zlyy.pojo.User;
import com.example.zlyy.pojo.bo.WxUserInfo;
import com.example.zlyy.handler.UserThreadLocal;
import com.example.zlyy.mapper.UserMapper;
import com.example.zlyy.service.AdminService;
import com.example.zlyy.service.UserService;
import com.example.zlyy.service.WxService;
import com.example.zlyy.util.AES;
import com.example.zlyy.util.JWTUtils;
import com.example.zlyy.util.WXCore;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.example.zlyy.util.RedisConstants.*;
import static com.example.zlyy.util.StringConstants.SESSION_URL;
import static jdk.jfr.internal.instrument.JDKEvents.initialize;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Resource
    private WxService wxService;
    
    @Resource
    private UserMapper userMapper;
    
    @Resource
    private AdminService adminService;
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    private static final DefaultRedisScript<Long> USERINFO_SCRIPT;
    static {
        USERINFO_SCRIPT = new DefaultRedisScript<>();
        USERINFO_SCRIPT.setLocation(new ClassPathResource("userInfo.lua"));
        USERINFO_SCRIPT.setResultType(Long.class);
    }
    
    @Value("${wxmini.appid}")
    private String appid;
    
    @Value("${wxmini.secret}")
    private String secret;
    
    @Override
    public R authLogin(WXAuth wxAuth) {
        /**
         * 1. 通过wxAuth的值, 要对它进行赋值
         * 2. 解密完成之后, 会或起到微信用户信息 其中包含openId, 性别, 昵称, 头像等信息
         * 3. openId是唯一的, 需要去user表中查询openId是否存在, 
         *      存在, 以此用户的身份登录成功; 
         *      不存在, 新用户, 注册流程, 登录成功
         * 4. 使用jwt技术, 生成一个token, 提供给前端
         *      token令牌, 用户在下次访问的时候, 携带token来访问
         * 5. 后端通过对token的验证, 知道此用户是否处于登录状态, 以及是哪个用户登录的
         */
        try {
            logger.debug("wxAuth.getExcryptedData(): {}, wxAuth.getSessionId(): {}, wxAuth.getIv(): {}", wxAuth.getExcryptedData(), wxAuth.getSessionId(), wxAuth.getIv());

            // TODO: 解密不出来openId
            String[] json = wxService.wxDecrypt(wxAuth.getExcryptedData(), wxAuth.getSessionId(), wxAuth.getIv());
            if (null == json || json.length < 3) {
                throw new Exception("Invalid json[] length");
            }
            logger.debug("wxUserInfoJson: {}", json[0]);
            WxUserInfo wxUserInfo = JSON.parseObject(json[0], WxUserInfo.class);
            logger.debug("wxUserInfo: {}", wxUserInfo.toString());
            
            String openId = json[1];
            String unionId = json[2];
            
            logger.debug("openId: {}, unionId: {}", openId, unionId);
            User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getOpenId, openId).last("limit 1"));
            
            UserDTO userDTO = new UserDTO();
            userDTO.from(wxUserInfo);
            userDTO.setOpenId(openId);
            userDTO.setWxUnionId(unionId);
            if (user == null) {
                // 注册
                return this.register(userDTO);
            } else {
                // 登录
                userDTO.setId(user.getId());
                
                // TODO: 判断openId是不是管理员, 分开给前端标识
//                String userOpenId = userDTO.getOpenId();
//                int count = adminService.count(new QueryWrapper<Admin>()
//                        .eq("open_id", userOpenId)
//                );
//                if (count == 0) {
//                    
//                }

                return this.login(userDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.error("authLogin failed");
    }

    @Override
    public R userInfo(Boolean refresh) {
        /**
         * 1. 根据token 来验证此token 是否有效
         * 2. refresh 如果为true 代表刷新 重新生成token和redis里面重新存储 续期
         * 3. false直接返回用户信息 -》 redis中 查询出来 直接返回
         * TODO: 哪里调用了它???
         */
        UserDTO userDTO = UserThreadLocal.get();
        
        logger.debug("userDTO after refresh: {}", userDTO.toString());
        
        if (refresh){
            
            // TODO: 续期token, 前后是不是一个token?
            
            String token = JWTUtils.sign(userDTO.getId());
            userDTO.setToken(token);

//            stringRedisTemplate.opsForValue().set(TOKEN + token, JSON.toJSONString(userDTO), 7, TimeUnit.DAYS);
            
            Long result = stringRedisTemplate.execute(
                    USERINFO_SCRIPT, 
                    Collections.emptyList(),
                    token, JSON.toJSONString(userDTO)
            );
            int r = result.intValue();
            if (r == 1) {
                logger.info("token已过期, userDTO: {}", JSON.toJSONString(userDTO));
            }
            if (r == 0) {
                return R.error("token 续期未知错误");
            }
            // TODO: 续期模型数据

        }

        return R.ok().put("userDto", userDTO);
        
    }

    
    @Override
    public R getSessionId(String code) {
        
        String url = SESSION_URL;
        logger.debug("addid: {}, secret: {}, code: {}", appid, secret, code);
        String replaceUrl = url.replace("{0}", appid).replace("{1}", secret).replace("{2}", code);
        logger.debug("replace url: {}", replaceUrl);
        String res = HttpUtil.get(replaceUrl);
        logger.debug("res(value of 'WX_SESSION_ID + uuid') : {}", res);
        try {
            String uuid = UUID.randomUUID().toString();
            logger.debug("uuid or sessionId to redis: {}", uuid);
            try {
//                redisMapper.set(WX_SESSION_ID + uuid, res, 30, TimeUnit.MINUTES);
                // TODO: 这一步没有存到redis, 但下一次再登录的时候就存进去了???
                stringRedisTemplate.opsForValue().set(WX_SESSION_ID + uuid, res, 30, TimeUnit.MINUTES);
                if (stringRedisTemplate.hasKey(WX_SESSION_ID + uuid)) {
                    return R.ok().put("sessionId", uuid);
                } else {
                    return R.error("set sessionId to redis failed");
                }
            } catch (Exception e) {
                logger.error("redis set sessionId error: {}", e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            logger.error("uuid generate error: {}", e);
        }
        return R.error("sessionId获取失败");
    }




    private R login(UserDTO userDTO) {
        String token = JWTUtils.sign(userDTO.getId());
        userDTO.setToken(token);
//        userDTO.setOpenId(null);
//        userDTO.setWxUnionId(null);
        
        logger.info("login userDTO: {}", userDTO);
        
        // 需要把token存入redis, value存为userDTO, 下次用户访问需要登录资源的时候, 可以根据token拿到用户的详细信息
        // 缓存
        
        stringRedisTemplate.opsForValue().set(TOKEN + token, JSON.toJSONString(userDTO), 7, TimeUnit.DAYS);
        
        // TODO: 2种方案: 1.非持久化数据随着token过期而消亡; 2.每次登录时先消除非持久化数据(如: 上次填了一半的东西)
        // TODO: I prefer 方案2
        
        return R.ok().put("userDTO", userDTO);
    }

    private R register(UserDTO userDTO) {
        // 注册之前 判断 用户是否存在
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        this.userMapper.insert(user);  // TODO: 没有存进数据库
        
        // TODO: 加一个拦截器, 以openId区分管理员, 对管理员的请求放行, 对外封装注解@NoAdminAuth, 在AdminHandler里判断是否是管理员
        
        userDTO.setId(user.getId());
        return this.login(userDTO);
    }
}
