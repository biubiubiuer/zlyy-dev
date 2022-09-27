package com.example.zlyy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zlyy.entity.UserInfo;
import com.example.zlyy.mapper.UserInfoMapper;
import com.example.zlyy.service.UserInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Override
    public boolean saveUserInfo(UserInfo userInfo) throws Exception {

        boolean isSave = save(userInfo);
        
        if (!isSave) {
            throw new Exception("save userInfo failed!");
        }

        return false;
    }
}
