package com.example.zlyy.service.impl;

import com.example.zlyy.common.R;
import com.example.zlyy.handler.UserThreadLocal;
import com.example.zlyy.mapper.IndicatorMapper;
import com.example.zlyy.pojo.Indicator;
import com.example.zlyy.pojo.bo.BloodBiochemistry;
import com.example.zlyy.pojo.bo.Poop;
import com.example.zlyy.pojo.bo.Urine;
import com.example.zlyy.pojo.bo.WholeBlood;
import com.example.zlyy.pojo.dto.UserDTO;
import com.example.zlyy.service.IndicatorService;
import com.example.zlyy.util.MyMapBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.example.zlyy.util.ArrayConstants.*;
import static com.example.zlyy.util.QuestionUtils.serveSingleQuestion;
import static com.example.zlyy.util.RedisConstants.*;

@Slf4j
@Service
public class IndicatorServiceImpl  implements IndicatorService {
    
    Logger logger = LoggerFactory.getLogger(IndicatorServiceImpl.class);
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    @Resource
    private IndicatorMapper indicatorMapper;


    Integer[] inputList_1 = initializeInputList(QUES_KEYS_1.length);
    Integer[] inputList_2 = initializeInputList(QUES_KEYS_2.length);
    Integer[] inputList_3 = initializeInputList(QUES_KEYS_3.length);
    Integer[] inputList_4 = initializeInputList(QUES_KEYS_4.length);

    private Integer[] initializeInputList(int len) {
        Integer[] in = new Integer[len];
        Arrays.fill(in, 0);
        return in;
    }


    
    private List<Map<String, Integer>> title2QidMaps = new ArrayList<Map<String, Integer>>() {
        {
            try {
                add((Map<String, Integer>) MyMapBuilder.build(QUES_KEYS_1, inputList_1));
                add((Map<String, Integer>) MyMapBuilder.build(QUES_KEYS_2, inputList_2));
                add((Map<String, Integer>) MyMapBuilder.build(QUES_KEYS_3, inputList_3));
                add((Map<String, Integer>) MyMapBuilder.build(QUES_KEYS_4, inputList_4));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

//        private Map<String, Integer> title2QidMap;

//    {
//        try {
//            title2QidMap = (Map<String, Integer>) MapBuilder.build(QUES_KEYS_1, inputList_1);
//        } catch (Exception e) {
//            logger.error("map build error: {}", e);
//            e.printStackTrace();
//        }
//    }
    
    @Override
    public R updateBiochemicalIndicators(BloodBiochemistry bloodBiochemistry, WholeBlood wholeBlood, Poop poop, Urine urine) {

        
        serveSingleQuestion(bloodBiochemistry, inputList_1, title2QidMaps.get(0));
        serveSingleQuestion(wholeBlood, inputList_2, title2QidMaps.get(1));
        serveSingleQuestion(poop, inputList_3, title2QidMaps.get(2));
        serveSingleQuestion(urine, inputList_4, title2QidMaps.get(3));
        
        
        String biochemicalIndicatorsStr_1 = StringUtils.join(inputList_1, ",");
        String biochemicalIndicatorsStr_2 = StringUtils.join(inputList_2, ",");
        String biochemicalIndicatorsStr_3 = StringUtils.join(inputList_3, ",");
        String biochemicalIndicatorsStr_4 = StringUtils.join(inputList_4, ",");
        
        String biochemicalIndicatorsStr = biochemicalIndicatorsStr_1 + "," + biochemicalIndicatorsStr_2 + ","
                + biochemicalIndicatorsStr_3 + "," + biochemicalIndicatorsStr_4;
        
        // TODO: openId, way1: redis and userthredlocal
        UserDTO userDTO = UserThreadLocal.get();
        if (userDTO != null && userDTO.getOpenId() != null) {
            stringRedisTemplate.opsForValue().set(OPENID + userDTO.getOpenId(), biochemicalIndicatorsStr);
            
            Indicator indicator = new Indicator();
            indicator.setOpenId(userDTO.getOpenId());
            indicator.setWxUnionId(userDTO.getWxUnionId());
            indicator.setBiochemicalIndicatorsStr(biochemicalIndicatorsStr);
            
            // TODO: 持久化到mysql
            indicatorMapper.saveIndicator(indicator);
            
            return R.ok("biochemicalIndicatorsStr set to redis success");
        } else if (userDTO == null) {
            return R.ok("userDTO is null");
        } else {
            return R.ok("openId is null");
        }
        
        
    }
}
