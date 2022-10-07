package com.example.zlyy.service;

import com.example.zlyy.common.R;
import com.example.zlyy.pojo.bo.BloodBiochemistry;
import com.example.zlyy.pojo.bo.Poop;
import com.example.zlyy.pojo.bo.Urine;
import com.example.zlyy.pojo.bo.WholeBlood;

public interface IndicatorService {
    R updateBiochemicalIndicators(BloodBiochemistry bloodBiochemistry, WholeBlood wholeBlood, Poop poop, Urine urine);
}
