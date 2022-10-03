package com.example.zlyy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zlyy.pojo.Patient;
import com.example.zlyy.pojo.bo.QuestionA;
import com.example.zlyy.mapper.QuestionAMapper;
import com.example.zlyy.service.QuestionAService;
import com.example.zlyy.util.PatientUtil;
import com.example.zlyy.util.QuestionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QuestionAServiceImpl extends ServiceImpl<QuestionAMapper, QuestionA> implements QuestionAService {
    
    private static final Logger logger = LoggerFactory.getLogger(QuestionAServiceImpl.class);
    
    @Override
    public void getQAThenSetPatient(QuestionA questionA, Patient patient) {

        try {
            patient.setHeight(QuestionUtils.removeSpace(questionA.getA01()));
            patient.setWeight(QuestionUtils.removeSpace(questionA.getA02()));
            patient.setBloodType(PatientUtil.parseBloodType(QuestionUtils.removeSpace(questionA.getA04())));
        } catch (Exception e) {
            logger.error("json resolve error" + e.getMessage(), e);
            e.printStackTrace();
        }

    }
}
