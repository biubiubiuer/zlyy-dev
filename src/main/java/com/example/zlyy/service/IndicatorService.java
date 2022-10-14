package com.example.zlyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zlyy.common.R;
import com.example.zlyy.pojo.Indicator;
import com.example.zlyy.pojo.bo.BloodBiochemistry;
import com.example.zlyy.pojo.bo.Poop;
import com.example.zlyy.pojo.bo.Urine;
import com.example.zlyy.pojo.bo.WholeBlood;

public interface IndicatorService extends IService<Indicator> {
    R saveBiochemicalIndicators(BloodBiochemistry bloodBiochemistry, WholeBlood wholeBlood, Poop poop, Urine urine);

    R updateBiochemicalIndicators(BloodBiochemistry bloodBiochemistry, WholeBlood wholeBlood, Poop poop, Urine urine);
}
