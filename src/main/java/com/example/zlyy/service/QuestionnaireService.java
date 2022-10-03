package com.example.zlyy.service;

import com.example.zlyy.pojo.bo.*;
import com.example.zlyy.common.R;

public interface QuestionnaireService {
    R updateQuestionnaire(
            QUserInfo QUserInfo, 
            QuestionA questionA, 
            QuestionB questionB, 
            QuestionC questionC, 
            QuestionD questionD, 
            QuestionE questionE, 
            QuestionF questionF, 
            MultiOptionQuestion multiOptionQuestion
    );
}
