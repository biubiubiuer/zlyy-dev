package com.example.zlyy.controller;

import com.example.zlyy.annotation.MultiRequestBody;
import com.example.zlyy.dto.PatientDTO;
import com.example.zlyy.dto.R;
import com.example.zlyy.entity.*;
import com.example.zlyy.service.PatientDTOService;
import com.example.zlyy.service.QuestionAService;
import com.example.zlyy.service.QuestionnaireService;
import com.example.zlyy.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

@Slf4j
@RestController
@RequestMapping("/questionnaire")
@ResponseBody
public class QuestionnaireController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionnaireController.class);

    @Resource
    private QuestionnaireService questionnaireService;
    
    @Resource
    private UserInfoService userInfoService;
    
    @Resource
    private QuestionAService questionAService;
    
    @Resource
    private PatientDTOService patientDTOService;


    @PostMapping(value = "/update", produces={"application/json;charset=UTF-8"})
    @ResponseBody
    public R saveQuestionnaire(
            @MultiRequestBody UserInfo userInfo,
            @MultiRequestBody QuestionA questionA, @MultiRequestBody QuestionB questionB, @MultiRequestBody QuestionC questionC,
            @MultiRequestBody QuestionD questionD, @MultiRequestBody QuestionE questionE, @MultiRequestBody QuestionF questionF,
            @MultiRequestBody MultiOptionQuestion multiOptionQuestion) throws Exception {


        R r = questionnaireService.updateQuestionnaire(
                userInfo, questionA, questionB, questionC, questionD, questionE, questionF, multiOptionQuestion
        );
        
        if (r.get("msg") == "success") {

            userInfo.setStmPoss((Double) r.get("modelRes"));

            PatientDTO patientDTO = new PatientDTO();

            if (submitPatientDTO(userInfo, questionA, patientDTO)) {
                patientDTOService.submitPatientInfo(patientDTO);
            }
        }
        
        
        return r;
    }


    @Transactional(rollbackFor = Exception.class)
    public Boolean submitPatientDTO(UserInfo userInfo, QuestionA questionA, PatientDTO patientDTO) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            userInfoService.submitUserInfo(userInfo, patientDTO);
            questionAService.submitQuestionA(questionA, patientDTO);
            patientDTO.setTime(df.format(System.currentTimeMillis()));
            return true;
        } catch (Exception e) {
            
        }
        
        return false;
    }




}
