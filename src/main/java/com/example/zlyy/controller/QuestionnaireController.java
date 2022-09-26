package com.example.zlyy.controller;

import com.example.zlyy.annotation.MultiRequestBody;
import com.example.zlyy.dto.R;
import com.example.zlyy.entity.*;
import com.example.zlyy.service.QuestionnaireService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/questionnaire")
@ResponseBody
public class QuestionnaireController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionnaireController.class);

    @Resource
    private QuestionnaireService questionnaireService;


    @PostMapping(value = "/update", produces={"application/json;charset=UTF-8", "text/xml;charset=UTF-8", "text/html;charset=UTF-8"})
//    @PostMapping(value = "/update")
    @ResponseBody
    public R saveQuestionnaire(
            @MultiRequestBody UserInfo userInfo,
            @MultiRequestBody QuestionA questionA, @MultiRequestBody QuestionB questionB, @MultiRequestBody QuestionC questionC,
            @MultiRequestBody QuestionD questionD, @MultiRequestBody QuestionE questionE, @MultiRequestBody QuestionF questionF,
            @MultiRequestBody MultiOptionQuestion multiOptionQuestion)  {


        return questionnaireService.updateQuestionnaire(
                userInfo, questionA, questionB, questionC, questionD, questionE, questionF, multiOptionQuestion
        );

    }




}
