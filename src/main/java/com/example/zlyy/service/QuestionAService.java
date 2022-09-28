package com.example.zlyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zlyy.dto.PatientDTO;
import com.example.zlyy.entity.QuestionA;

import java.sql.SQLException;

public interface QuestionAService extends IService<QuestionA> {
    void submitQuestionA(QuestionA questionA, PatientDTO patientDTO) throws SQLException;
}
