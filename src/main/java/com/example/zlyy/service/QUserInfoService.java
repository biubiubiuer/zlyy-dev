package com.example.zlyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zlyy.pojo.Patient;
import com.example.zlyy.pojo.bo.QUserInfo;

import java.sql.SQLException;

public interface QUserInfoService extends IService<QUserInfo> {
    void getInfoThenSetPatient(QUserInfo qUserInfo, Patient patient) throws SQLException;
}
