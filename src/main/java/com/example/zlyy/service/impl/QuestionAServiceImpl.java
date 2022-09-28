package com.example.zlyy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zlyy.dto.PatientDTO;
import com.example.zlyy.entity.QuestionA;
import com.example.zlyy.mapper.QuestionAMapper;
import com.example.zlyy.service.QuestionAService;
import com.example.zlyy.util.PatientUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Slf4j
@Service
public class QuestionAServiceImpl extends ServiceImpl<QuestionAMapper, QuestionA> implements QuestionAService {
    
    private static final Logger logger = LoggerFactory.getLogger(QuestionAServiceImpl.class);
    
    @Override
    public void submitQuestionA(QuestionA questionA, PatientDTO patientDTO) {

        try {
            patientDTO.setHeight(questionA.getA01().toString());
            patientDTO.setWeight(questionA.getA02().toString());
            patientDTO.setBloodType(PatientUtil.parseBloodType(questionA.getA04()));
        } catch (Exception e) {
            logger.error("json resolve error" + e.getMessage(), e);
            e.printStackTrace();
        }

    }
}
