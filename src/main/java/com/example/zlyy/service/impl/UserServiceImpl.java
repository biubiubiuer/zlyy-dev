package com.example.zlyy.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.zlyy.common.R;
import com.example.zlyy.pojo.dto.UserDTO;
import com.example.zlyy.pojo.bo.WXAuth;
import com.example.zlyy.pojo.User;
import com.example.zlyy.pojo.WxUserInfo;
import com.example.zlyy.handler.UserThreadLocal;
import com.example.zlyy.mapper.UserMapper;
import com.example.zlyy.service.UserService;
import com.example.zlyy.service.WxService;
import com.example.zlyy.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.example.zlyy.util.RedisConstants.*;
import static com.example.zlyy.util.StringConstants.SESSION_URL;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Resource
    private WxService wxService;
    
    @Resource
    private UserMapper userMapper;
    
//    @Resource
//    private RedisMapper redisMapper;
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
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
            String json = wxService.wxDecrypt(wxAuth.getExcryptedData(), wxAuth.getSessionId(), wxAuth.getIv());
            WxUserInfo wxUserInfo = JSON.parseObject(json, WxUserInfo.class);
            String openId = wxUserInfo.getOpenId();
            logger.debug("openId: {}", openId);
            
            // TODO: openId: 用户唯一标识 so 可以把同一用户多个接口暂存的东西放到mysql ?
            
            // TODO: 为什么要limit1? 难道会查到很多个? -> 没事, 不重要
            User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getOpenId, openId).last("limit 1"));
            
            UserDTO userDTO = new UserDTO();
            userDTO.from(wxUserInfo);
            if (user == null) {
                // 注册
                return this.register(userDTO);
            } else {
                // 登录
                userDTO.setId(user.getId());
                return this.login(userDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.error();
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
        if (refresh){
            
            // 续期token, 前后应该不是一个token
            
            String token = JWTUtils.sign(userDTO.getId());
            userDTO.setToken(token);
//            redisMapper.set(TOKEN_KEY + token, JSON.toJSONString(userDTO), 7, TimeUnit.DAYS);
            stringRedisTemplate.opsForValue().set(TOKEN + token, JSON.toJSONString(userDTO), 7, TimeUnit.DAYS);
            
            // TODO: 续期模型数据
            
        }
        return R.ok().put("userDto", userDTO);
        
    }

    
    @Override
    public R getSessionId(String code) {
        
        String url = SESSION_URL;
        logger.debug("addid: {}, secret: {}, code: {}", appid, secret, code);
        String replaceUrl = url.replace("{0}", appid).replace("{1}", secret).replace("{2}", code);
        // TODO: https://api.weixin.qq.com/sns/jscode2session?appid=wx6871b064c3e82c44&secret=a2b5ad76f54a9a2caf7abca03a40ebc8&js_code=073cxoFa1CZ90E0mQxJa1dwTjX3cxoFA,003LyzFa1Jqt0E0nYvHa1rYZcx3LyzFt&grant_type=authorization_code
        logger.debug("replace url: {}", replaceUrl);
        // TODO: {"errcode":40029,"errmsg":"invalid code, rid: 633b307f-0dea51f9-4d438906"}
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
        userDTO.setOpenId(null);
        userDTO.setWxUnionId(null);
        
        // 需要把token存入redis, value存为userDTO, 下次用户访问需要登录资源的时候, 可以根据token拿到用户的详细信息
        // 缓存
//        redisMapper.set(TOKEN_KEY + token, JSON.toJSONString(userDTO), 7, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(TOKEN_KEY + token, JSON.toJSONString(userDTO), 7, TimeUnit.DAYS);
        
        // TODO: 2种方案: 1.非持久化数据随着token过期而消亡; 2.每次登录时先消除非持久化数据(如: 上次填了一半的东西)
        // TODO: I prefer 方案2
//        if (redisMapper.keyIsExists(TOKEN_KEY + token + REDIS_INFIX[0])) {
//            redisMapper.del(TOKEN_KEY + token + REDIS_INFIX[0]);
//        }
        
        
        return R.ok().put("userDTO", userDTO);
    }

    private R register(UserDTO userDTO) {
        // 注册之前 判断 用户是否存在
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        this.userMapper.insert(user);  // TODO: 没有存进数据库
        userDTO.setId(user.getId());
        return this.login(userDTO);
    }
}
