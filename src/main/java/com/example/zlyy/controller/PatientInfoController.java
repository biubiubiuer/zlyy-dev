package com.example.zlyy.controller;

import com.example.zlyy.dto.R;
import com.example.zlyy.service.PatientDTOService;
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
    private PatientDTOService patientDtoService;

    @PostMapping(value = "/list", produces={"application/json;charset=UTF-8"})
    @ResponseBody
    public R getAllPatients() {
        return patientDtoService.getPatientListByName();
    }
    
}
