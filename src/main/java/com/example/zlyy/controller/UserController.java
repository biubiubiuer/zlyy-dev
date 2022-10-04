package com.example.zlyy.controller;

import com.example.zlyy.annotation.NoAuth;
import com.example.zlyy.common.R;
import com.example.zlyy.pojo.bo.WXAuth;
import com.example.zlyy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    
    Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Resource
    public UserService userService;
    

    @NoAuth
    @GetMapping("/getSessionId")
    public R getSessionId(@RequestParam String code) {
        return userService.getSessionId(code);
    }
    
    
    @NoAuth
    @PostMapping(value= "/autoLogin", produces={"application/json;charset=UTF-8"})
        public R authLogin(@RequestBody WXAuth wxAuth) {
        R r = userService.authLogin(wxAuth);
        logger.info("{}", r);
        return r;
    }

    /**
     * 登陆成功后, 根据token验证并返回用户信息
     * @param refresh
     * @return
     */
    @GetMapping("userInfo")
    public R userInfo(Boolean refresh) {
        return userService.userInfo(refresh);
    }
}
