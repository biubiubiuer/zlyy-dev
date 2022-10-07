package com.example.zlyy.service;

import com.example.zlyy.common.R;
import com.example.zlyy.pojo.bo.WXAuth;

public interface UserService {

    R authLogin(WXAuth wxAuth);


    R userInfo(Boolean refresh);
    
    
    R getSessionId(String code);

}
