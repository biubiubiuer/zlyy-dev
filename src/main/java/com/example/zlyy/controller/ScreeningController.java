package com.example.zlyy.controller;

import com.example.zlyy.annotation.MultiRequestBody;
import com.example.zlyy.common.R;
import com.example.zlyy.pojo.bo.BloodBiochemistry;
import com.example.zlyy.pojo.bo.Poop;
import com.example.zlyy.pojo.bo.Urine;
import com.example.zlyy.pojo.bo.WholeBlood;
import com.example.zlyy.service.ScreeningService;
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
@RequestMapping("/screen")
@ResponseBody
public class ScreeningController {
    
    private static final Logger logger = LoggerFactory.getLogger(ScreeningController.class);
    
    @Resource
    private ScreeningService screeningService;

    @PostMapping(value = "/update", produces={"application/json;charset=UTF-8"})
    @ResponseBody
    public R saveQuestionnaire(
            @MultiRequestBody BloodBiochemistry bloodBiochemistry, 
            @MultiRequestBody WholeBlood wholeBlood, 
            @MultiRequestBody Poop poop, 
            @MultiRequestBody Urine urine) {
        return screeningService.updateBiochemicalIndicators(bloodBiochemistry, wholeBlood, poop, urine);
    }
    
}
