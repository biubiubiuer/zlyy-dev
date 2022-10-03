package com.example.zlyy.controller;

import com.example.zlyy.annotation.MultiRequestBody;
import com.example.zlyy.annotation.NoAuth;
import com.example.zlyy.pojo.bo.*;
import com.example.zlyy.common.R;
import com.example.zlyy.pojo.*;
import com.example.zlyy.service.PatientService;
import com.example.zlyy.service.QuestionAService;
import com.example.zlyy.service.QuestionnaireService;
import com.example.zlyy.service.QUserInfoService;
import lombok.extern.slf4j.Slf4j;
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
    private QUserInfoService QUserInfoService;
    
    @Resource
    private QuestionAService questionAService;
    
    @Resource
    private PatientService patientService;


//    @NoAuth
    @PostMapping(value = "/update", produces={"application/json;charset=UTF-8"})
    @ResponseBody
    public R saveQuestionnaire(
            @MultiRequestBody QUserInfo qUserInfo,
            @MultiRequestBody QuestionA questionA, @MultiRequestBody QuestionB questionB, @MultiRequestBody QuestionC questionC,
            @MultiRequestBody QuestionD questionD, @MultiRequestBody QuestionE questionE, @MultiRequestBody QuestionF questionF,
            @MultiRequestBody MultiOptionQuestion multiOptionQuestion) throws Exception {


        R r = questionnaireService.updateQuestionnaire(
                qUserInfo, questionA, questionB, questionC, questionD, questionE, questionF, multiOptionQuestion
        );
        
        if ("success".equals(r.get("msg"))) {

            qUserInfo.setStmPoss(String.valueOf(r.get("modelRes")));
            
            Patient patient = new Patient();
            
//            PatientDTO patientDTO = new PatientDTO();

            if (getInfoAndQAThenSetPatient(qUserInfo, questionA, patient)) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = df.format(System.currentTimeMillis());
                patientService.submitPatientInfo(patient, time);
            }
        }
        
        
        return r;
    }


    @Transactional(rollbackFor = Exception.class)
    public Boolean getInfoAndQAThenSetPatient(QUserInfo QUserInfo, QuestionA questionA, Patient patient) throws Exception {
        
        try {
            QUserInfoService.getInfoThenSetPatient(QUserInfo, patient);
            questionAService.getQAThenSetPatient(questionA, patient);
            return true;
        } catch (Exception e) {
            
        }
        
        return false;
    }




}
