package com.example.zlyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zlyy.common.Question;
import com.example.zlyy.dto.R;
import com.example.zlyy.entity.*;

public interface QuestionnaireService {
    R updateQuestionnaire(
            UserInfo userInfo, 
            QuestionA questionA, 
            QuestionB questionB, 
            QuestionC questionC, 
            QuestionD questionD, 
            QuestionE questionE, 
            QuestionF questionF, 
            MultiOptionQuestion multiOptionQuestion
    );
}
