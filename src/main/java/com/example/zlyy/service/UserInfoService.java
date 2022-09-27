package com.example.zlyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zlyy.entity.UserInfo;

public interface UserInfoService extends IService<UserInfo> {
    boolean saveUserInfo(UserInfo userInfo) throws Exception;
}
