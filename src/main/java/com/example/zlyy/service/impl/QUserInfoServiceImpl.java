package com.example.zlyy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zlyy.pojo.Patient;
import com.example.zlyy.pojo.bo.QUserInfo;
import com.example.zlyy.mapper.QUserInfoMapper;
import com.example.zlyy.service.QUserInfoService;
import com.example.zlyy.util.PatientUtil;
import com.example.zlyy.util.QuestionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QUserInfoServiceImpl extends ServiceImpl<QUserInfoMapper, QUserInfo> implements QUserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(QUserInfoServiceImpl.class);

    @Override
    public void getInfoThenSetPatient(QUserInfo qUserInfo, Patient patient) {

        try {
            patient.setName(qUserInfo.getName());
            patient.setSex("1".equals(QuestionUtils.removeSpace(qUserInfo.getSex())) ? "女" : "男");
            patient.setPhoneNumber(QuestionUtils.removeSpace(qUserInfo.getPhoneNumber()));
            patient.setBirthYear(QuestionUtils.removeSpace(qUserInfo.getBirthYear()));
            patient.setNation(PatientUtil.parseNation(QuestionUtils.removeSpace(qUserInfo.getNation())));
            patient.setAddress(PatientUtil.parseAddress(QuestionUtils.removeSpace(qUserInfo.getAddress())));
            patient.setIdCard(QuestionUtils.removeSpace(qUserInfo.getIdCard()));

        } catch (Exception e) {
            logger.error("json resolve error" + e.getMessage(), e);
            e.printStackTrace();
        }
        
    }
}
