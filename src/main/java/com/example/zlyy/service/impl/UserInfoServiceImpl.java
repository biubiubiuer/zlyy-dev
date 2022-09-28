package com.example.zlyy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zlyy.dto.PatientDTO;
import com.example.zlyy.entity.QuestionA;
import com.example.zlyy.entity.UserInfo;
import com.example.zlyy.mapper.UserInfoMapper;
import com.example.zlyy.service.UserInfoService;
import com.example.zlyy.util.PatientUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Slf4j
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Override
    public void submitUserInfo(UserInfo userInfo, PatientDTO patientDTO) {

        try {
            patientDTO.setName(userInfo.getName());
            patientDTO.setSex(userInfo.getSex() == 1 ? "女" : "男");
            patientDTO.setBirthYear(userInfo.getBirthYear().toString());
            patientDTO.setNation(PatientUtil.parseNation(userInfo.getNation()));
            patientDTO.setAddress(PatientUtil.parseAddress(userInfo.getAddress()));
            patientDTO.setIdCard(userInfo.getIdCard());
            patientDTO.setStmPoss(userInfo.getStmPoss().toString());

        } catch (Exception e) {
            logger.error("json resolve error" + e.getMessage(), e);
            e.printStackTrace();
        }
        
        
        
        
        
        
    }
}
