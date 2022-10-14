package com.example.zlyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zlyy.common.R;
import com.example.zlyy.pojo.User;
import com.example.zlyy.pojo.bo.WXAuth;

public interface UserService extends IService<User> {

    R authLogin(WXAuth wxAuth);


    R userInfo(Boolean refresh);
    
    
    R getSessionId(String code);

}
