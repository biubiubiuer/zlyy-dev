package com.example.zlyy.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import com.example.zlyy.util.ClassificationModel;
import com.example.zlyy.util.ModelStringUtil;
import com.example.zlyy.util.MyMapBuilder;
import com.example.zlyy.util.QuestionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.dmg.pmml.FieldName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.example.zlyy.util.ArrayConstants.*;
import static com.example.zlyy.util.QuestionUtils.serveSingleQuestion;
import static com.example.zlyy.util.RedisConstants.*;
import static com.example.zlyy.util.StringConstants.PMML_PATH_2;

@Slf4j
@Service
public class IndicatorServiceImpl extends ServiceImpl<IndicatorMapper, Indicator> implements IndicatorService {
    
    Logger logger = LoggerFactory.getLogger(IndicatorServiceImpl.class);

    private static final DefaultRedisScript<Long> INDI_SCRIPT;
    static {
        INDI_SCRIPT = new DefaultRedisScript<>();
        INDI_SCRIPT.setLocation(new ClassPathResource("indicator.lua"));
        INDI_SCRIPT.setResultType(Long.class);
    }
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    @Resource
    private IndicatorMapper indicatorMapper;
    
    @Resource
    private IndicatorService indicatorService;


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
                logger.error("title2QidMaps set failed, {}", e.getMessage());
                e.printStackTrace();
            }
        }
    };
    
    @Override
    public R saveBiochemicalIndicators(BloodBiochemistry bloodBiochemistry, WholeBlood wholeBlood, Poop poop, Urine urine) {

        
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
        
        
        UserDTO userDTO = UserThreadLocal.get();
        if (userDTO != null && userDTO.getOpenId() != null) {
            
            String openId = userDTO.getOpenId();
            
            // TODO: 存的时候, redis只保留最新的, mysql保留所有操作记录
//            stringRedisTemplate.opsForValue().set(OPENID + userDTO.getOpenId() + INDI_INFIX, biochemicalIndicatorsStr);


            boolean b = extracted(biochemicalIndicatorsStr, userDTO);
            if (!b) {
                logger.error("bio save/update to redis failed");
            }



            // TODO: 先根据openId查, 再确定改不改?
            int count = indicatorService.count(new QueryWrapper<Indicator>().eq("open_id", openId));
            if (count == 0) {
                Indicator indicator = new Indicator();
                indicator.setOpenId(openId);
                indicator.setWxUnionId(userDTO.getWxUnionId());
                indicator.setBiochemicalIndicatorsStr(biochemicalIndicatorsStr);
                // 插入
                indicatorMapper.insert(indicator);
            } else {
                // 更新生化指标
                LambdaUpdateWrapper<Indicator> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(Indicator::getBiochemicalIndicatorsStr, biochemicalIndicatorsStr);
                indicatorMapper.update(null, updateWrapper);
            }
            
            return R.ok("biochemicalIndicatorsStr set to redis and mysql success");
        } else if (userDTO == null) {
            return R.ok("userDTO is null");
        } else {
            return R.ok("openId is null");
        }
        
        
    }

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
            
            String openId = userDTO.getOpenId();
            
//            stringRedisTemplate.opsForValue().set(OPENID + userDTO.getOpenId() + INDI_INFIX, biochemicalIndicatorsStr);

            boolean b = extracted(biochemicalIndicatorsStr, userDTO);
            if (!b) {
                logger.error("bio save/update to redis failed");
            }


            int count = indicatorService.count(new QueryWrapper<Indicator>().eq("open_id", openId));
            if (count == 0) {
                Indicator indicator = new Indicator();
                indicator.setOpenId(userDTO.getOpenId());
                indicator.setWxUnionId(userDTO.getWxUnionId());
                indicator.setBiochemicalIndicatorsStr(biochemicalIndicatorsStr);
                // 插入
                indicatorMapper.insert(indicator);
            } else {
                // 更新生化指标
                LambdaUpdateWrapper<Indicator> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(Indicator::getBiochemicalIndicatorsStr, biochemicalIndicatorsStr);
                indicatorMapper.update(null, updateWrapper);
            }


//            QueryWrapper<Indicator> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("open_id", openId);
//            Indicator indicator = indicatorMapper.selectOne(queryWrapper);
            
            Indicator indicator = null;
            try {
                indicator = indicatorMapper.selectOne(Wrappers.<Indicator>lambdaQuery().eq(Indicator::getOpenId, openId).last("limit 1"));
            } catch (Exception e) {
                logger.error("indicator query failed, {}", e.getMessage());
                e.printStackTrace();
            }

            // 结合模型1暂存数据得到最终预测结果
            String questionnaireStr = indicator.getQuestionnaireStr();
            if (StringUtils.isBlank(questionnaireStr)) {
                logger.error("questionnaireStr cannot be blank while using model2");
                return R.error("questionnaireStr cannot be blank while using model2");
            }
            questionnaireStr += ",";
            String[] split1 = questionnaireStr.split(",");
            int[] array1 = Arrays.asList(split1).stream().mapToInt(Integer::parseInt).toArray();
            biochemicalIndicatorsStr += ",";
            String[] split2 = biochemicalIndicatorsStr.split(",");
            int[] array2 = Arrays.asList(split2).stream().mapToInt(Integer::parseInt).toArray();
            int[] concatInputList = QuestionUtils.concatenate(array1, array2);

            ClassificationModel clf2 = new ClassificationModel(PMML_PATH_2);
            Map<FieldName, Number> waitPreSample2 = new HashMap<>();
            for (int i = 0; i < concatInputList.length; i++) {
                waitPreSample2.put(new FieldName(String.valueOf(i)), concatInputList[i]);
            }
            
//            String modelRes = clf2.predictProba(waitPreSample2).toString().substring(12, 17);

            String modelRes = ModelStringUtil.parseModelString(clf2.predictProba(waitPreSample2).toString());
            
            logger.info("modelRes: {} ", modelRes);
            
            return R.ok().put("modelRes", modelRes);
            
        } else if (userDTO == null) {
            return R.ok("userDTO is null");
        } else {
            return R.ok("openId is null");
        }
    }

    private boolean extracted(String biochemicalIndicatorsStr, UserDTO userDTO) {
        Long result = stringRedisTemplate.execute(
                INDI_SCRIPT,
                Collections.emptyList(),
                userDTO.getOpenId(), biochemicalIndicatorsStr
        );

        int r = result.intValue();
        if (r != 0) {
            return true;
        } else {
            return false;
        }
    }
}
