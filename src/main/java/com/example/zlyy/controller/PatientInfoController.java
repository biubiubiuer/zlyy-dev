package com.example.zlyy.controller;

import com.example.zlyy.annotation.NoAuth;
import com.example.zlyy.annotation.NotAdmin;
import com.example.zlyy.common.R;
import com.example.zlyy.service.PatientService;
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
@RequestMapping("/centre")
@ResponseBody
public class PatientInfoController {

    private static final Logger logger = LoggerFactory.getLogger(PatientInfoController.class);

    @Resource
    private PatientService patientService;


    @NoAuth
    @NotAdmin
    @PostMapping(value = "/list", produces={"application/json;charset=UTF-8"})
    public R getAllPatients() {
        return patientService.getPatientListByName();
    }

    @NoAuth
    @NotAdmin
    @PostMapping(value = "/weekList", produces={"application/json;charset=UTF-8"})
    public R getWeekPatients() {
        return patientService.getWeekPatients();
    }

    @NoAuth
    @NotAdmin
    @PostMapping(value = "/monthList", produces={"application/json;charset=UTF-8"})
    public R getMonthPatients() {
        return patientService.getMonthPatients();
    }

    @NoAuth
    @NotAdmin
    @PostMapping(value = "/yearList", produces={"application/json;charset=UTF-8"})
    public R getYearPatients() {
        return patientService.getYearPatients();
    }
    
}
