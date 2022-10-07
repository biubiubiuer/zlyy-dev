package com.example.zlyy.controller;

import com.example.zlyy.annotation.NoAuth;
import com.example.zlyy.annotation.NotAdmin;
import com.example.zlyy.common.R;
import com.example.zlyy.pojo.bo.WXAuth;
import com.example.zlyy.service.UserService;
import com.example.zlyy.util.AES;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.Map;

import static com.example.zlyy.util.RedisConstants.WX_SESSION_ID;


@Slf4j
@RestController
@RequestMapping("/user")
@ResponseBody
public class UserController {
    
    Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Resource
    public UserService userService;
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    

    @NoAuth
    @NotAdmin
    @GetMapping("/getSessionId")
    public R getSessionId(@RequestParam String code) {
        logger.debug("code in controller: {}", code);
        return userService.getSessionId(code);
    }
    
    
    @NoAuth
    @NotAdmin
    @PostMapping(value= "/autoLogin", produces={"application/json;charset=UTF-8"})
        public R authLogin(@RequestBody WXAuth wxAuth) {
        logger.debug("autoLogin bagin: {}", wxAuth.toString());
        R r = userService.authLogin(wxAuth);
        logger.info("{}", r);
        return r;
    }

    /**
     * 登陆成功后, 根据token验证并返回用户信息
     * @param refresh
     * @return
     */
    @NotAdmin
    @GetMapping("userInfo")
    public R userInfo(Boolean refresh) {
        logger.debug("refresh: {}", refresh);
        return userService.userInfo(refresh);
    }

    
    
}
