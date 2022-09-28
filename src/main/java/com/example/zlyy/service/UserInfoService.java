package com.example.zlyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zlyy.dto.PatientDTO;
import com.example.zlyy.entity.QuestionA;
import com.example.zlyy.entity.UserInfo;

import java.sql.SQLException;

public interface UserInfoService extends IService<UserInfo> {
    void submitUserInfo(UserInfo userInfo, PatientDTO patientDTO) throws SQLException;
}