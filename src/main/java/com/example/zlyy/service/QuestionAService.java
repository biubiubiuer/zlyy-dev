package com.example.zlyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zlyy.pojo.Patient;
import com.example.zlyy.pojo.bo.QuestionA;

import java.sql.SQLException;

public interface QuestionAService extends IService<QuestionA> {
    void getQAThenSetPatient(QuestionA questionA, Patient patient) throws SQLException;
}
