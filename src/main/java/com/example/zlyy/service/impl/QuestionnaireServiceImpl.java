package com.example.zlyy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.zlyy.handler.UserThreadLocal;
import com.example.zlyy.mapper.IndicatorMapper;
import com.example.zlyy.mapper.UserMapper;
import com.example.zlyy.pojo.Admin;
import com.example.zlyy.pojo.Indicator;
import com.example.zlyy.pojo.User;
import com.example.zlyy.pojo.bo.*;
import com.example.zlyy.common.R;
import com.example.zlyy.pojo.dto.UserDTO;
import com.example.zlyy.service.IndicatorService;
import com.example.zlyy.service.QuestionnaireService;
import com.example.zlyy.service.UserService;
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
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.zlyy.util.ArrayConstants.QUES_KEYS;
import static com.example.zlyy.util.ArrayConstants.QUES_VALUES;
import static com.example.zlyy.util.QuestionUtils.jsonKey2InputList;
import static com.example.zlyy.util.QuestionUtils.serveSingleQuestion;
import static com.example.zlyy.util.RedisConstants.*;
import static com.example.zlyy.util.StringConstants.PMML_PATH;
import static com.example.zlyy.util.StringConstants.PMML_PATH_2;

@Slf4j
@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionnaireServiceImpl.class);

    private static final DefaultRedisScript<Long> QUES_SCRIPT;
    static {
        QUES_SCRIPT = new DefaultRedisScript<>();
        QUES_SCRIPT.setLocation(new ClassPathResource("questionnaire.lua"));
        QUES_SCRIPT.setResultType(Long.class);
    }
    
    @Resource
    private UserMapper userMapper;
    
    @Resource
    private IndicatorMapper indicatorMapper;
    
    @Resource
    private IndicatorService indicatorService;
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    Integer[] inputList = initializeInputList();

    private Integer[] initializeInputList() {
        Integer[] in = new Integer[109];
        Arrays.fill(in, -3);
        return in;
    }
    
    private Map<String, Integer> title2QidMap;

    {
        try {
            title2QidMap = (Map<String, Integer>) MyMapBuilder.build(QUES_KEYS, QUES_VALUES);
        } catch (Exception e) {
            logger.error("map build error: {}", e);
            e.printStackTrace();
        }
    }


    @Override
    public R updateQuestionnaire(
            QUserInfo qUserInfo,
            QuestionA questionA, QuestionB questionB, QuestionC questionC,
            QuestionD questionD, QuestionE questionE, QuestionF questionF,
            MultiOptionQuestion multiOptionQuestion
    ) {


        // userInfo
        List<Field> fields1 = Arrays.stream(qUserInfo.getClass().getDeclaredFields()).filter(f -> {
            String name = f.getName();
            return !"id".equals(name) && !"serialVersionUID".equals(name);
        }).collect(Collectors.toList());
        
        
        for (Field field : fields1) {
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), qUserInfo.getClass());
                Method readMethod = descriptor.getReadMethod();
                String fieldName = descriptor.getName();
                
                try {
                    Object o = readMethod.invoke(qUserInfo);
                    if ("sex".equals(fieldName) || "birthYear".equals(fieldName) || "durable".equals(fieldName)) {
                        int intValue;
                        if ("sex".equals(fieldName)) {
                            intValue = Integer.valueOf(QuestionUtils.removeSpace((String) o));
                        } else if ("birthYear".equals(fieldName)) {
                            intValue = Integer.valueOf(QuestionUtils.removeSpace((String) o));
                        } else {
                            intValue = Integer.valueOf(QuestionUtils.removeSpace((String) o));
                        }
                        jsonKey2InputList(fieldName, o, intValue, qUserInfo, inputList, title2QidMap);
                    }
                } catch (InvocationTargetException e) {
                    logger.error("invoke error: {}", e);
                    e.printStackTrace();
                }
            } catch (IntrospectionException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        // A B C D E F
        // 反射获取属性列表
        serveSingleQuestion(questionA, inputList, title2QidMap);
        serveSingleQuestion(questionB, inputList, title2QidMap);
        serveSingleQuestion(questionC, inputList, title2QidMap);
        serveSingleQuestion(questionD, inputList, title2QidMap);
        serveSingleQuestion(questionD, inputList, title2QidMap);
        serveSingleQuestion(questionE, inputList, title2QidMap);
        serveSingleQuestion(questionF, inputList, title2QidMap);
        
        
        List<Field> fields3 = Arrays.stream(multiOptionQuestion.getClass().getDeclaredFields()).filter(f -> {
            String name = f.getName();
            //过滤掉不需要修改的属性
            return !"id".equals(name) && !"serialVersionUID".equals(name);
        }).collect(Collectors.toList());
        
        for (Field field : fields3) {
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), multiOptionQuestion.getClass());
                Method readMethod = descriptor.getReadMethod();
                Map<String, Object> o = (Map<String, Object>) readMethod.invoke(multiOptionQuestion);
                if (o != null && o.containsKey("options") && o.get("options") != null) {
                    Integer optionNum = (Integer) o.get("options");
                    // check-box mapping
                    int[] intValues = new int[optionNum];
                    for (int i = 0; i < intValues.length; i++) {
                        intValues[i] = -3;
                    }
                    if (o.containsKey("value") && o.get("value") != null) {
                        char[] value = serveValueStr(o.get("value").toString()).toCharArray();
                        for (char c : value) {
                            intValues[c - 'a'] = 1;
                        }
                    }
                    // mapping
                    String fieldName = descriptor.getName();
                    try {
                        int indexValue = title2QidMap.get(fieldName);
                        for (int i = 0; i < optionNum; i++) {
                            inputList[indexValue + i] = intValues[i];
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                

            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

//        NumberFormat format = NumberFormat.getPercentInstance();
//        format.setMaximumFractionDigits(3);
        
        
        // 结合模型2
        try {
            logger.debug("before threadLocal get");
            UserDTO userDTO = UserThreadLocal.get();
            logger.debug("after threadLocal get, and userDTO: {}", userDTO.toString());
            String openId = userDTO.getOpenId();
            logger.debug("openId: {}", openId);

            User user = null;
            try {
                user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getOpenId, openId).last("limit 1"));
            } catch (Exception e) {
                logger.error("user query error, {}", e.getMessage());
                e.printStackTrace();
            }
            
            logger.debug("user after mapper: {}", user.toString());
            
            boolean isModel2 = false;
            if (user != null) {
                isModel2 = isCaseModel2(user, openId);
            }

            logger.info("into model 1");

            ClassificationModel clf1 = new ClassificationModel(PMML_PATH);
            Map<FieldName, Number> waitPreSample1 = new HashMap<>();
            for (int i = 0; i < inputList.length; i++) {
                waitPreSample1.put(new FieldName(String.valueOf(i)), inputList[i]);
            }

            logger.debug("waitPreSample size of model 1: {}", waitPreSample1.size());

            String questionnaireStr = StringUtils.join(inputList, ",");
            // userDTO 已经在前面get到了
            // redis mysql 存储当前调查问卷结果
            if (userDTO != null && userDTO.getOpenId() != null) {
                
                // TODO: 这个存入redis也得保证最新 & 唯一
//                stringRedisTemplate.opsForValue().set(userDTO.getOpenId() + QUES_INFIX, questionnaireStr);

                Long result = stringRedisTemplate.execute(
                        QUES_SCRIPT,
                        Collections.emptyList(),
                        userDTO.getOpenId(), questionnaireStr
                );

                int r = result.intValue();
                if (r == 0) {
                    logger.error("ques save/update to redis failed");
                }


                // 不管用不用模型2, 模型1结果都要暂存, 因为生化指标后面可能还会更新
                int count = indicatorService.count(new QueryWrapper<Indicator>().eq("open_id", openId));
                if (count == 0) {
                    Indicator indicator = new Indicator();
                    indicator.setOpenId(userDTO.getOpenId());
                    indicator.setWxUnionId(userDTO.getWxUnionId());
                    indicator.setQuestionnaireStr(questionnaireStr);
                    // 插入
                    indicatorMapper.insert(indicator);
                } else {
                    // 更新调查问卷指标
                    LambdaUpdateWrapper<Indicator> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.set(Indicator::getQuestionnaireStr, questionnaireStr);
                    indicatorMapper.update(null, updateWrapper);
                }
                
                
//                indicatorMapper.updateQuestionnaireStr(indicator);
            }

            // TODO: map -> str -> redis -> mysql

//            String modelRes1 = clf1.predictProba(waitPreSample1).toString().substring(12, 17);

            String modelRes1 = ModelStringUtil.parseModelString(clf1.predictProba(waitPreSample1).toString());
            logger.info("modelRes1: {}", modelRes1);
            
            if (isModel2) {
                
                logger.info("into model 2");
                
                String biochemicalIndicatorsStr = stringRedisTemplate.opsForValue().get(OPENID + user.getOpenId() + INDI_INFIX);
                biochemicalIndicatorsStr += ",";
                String[] split = biochemicalIndicatorsStr.split(",");
                int[] array = Arrays.asList(split).stream().mapToInt(Integer::parseInt).toArray();

                // 两个数组拼接
                Integer[] concatInputList = (Integer[]) QuestionUtils.concatenate(inputList, array);

                ClassificationModel clf2 = new ClassificationModel(PMML_PATH_2);
                Map<FieldName, Number> waitPreSample2 = new HashMap<>();
                for (int i = 0; i < concatInputList.length; i++) {
                    waitPreSample2.put(new FieldName(String.valueOf(i)), concatInputList[i]);
                }

//                String modelRes2 = clf2.predictProba(waitPreSample2).toString().substring(12, 17);
                
                String modelRes2 = ModelStringUtil.parseModelString(clf2.predictProba(waitPreSample2).toString());
                
                logger.info("modelRes2: {}", modelRes2);

                return R.ok().put("modelRes", modelRes2);

            } else {
                return R.ok().put("modelRes", modelRes1);
            }
            
        } catch (Exception e) {
            logger.error("userDTO get error, {}", e.getMessage());
        }

        return R.error("questionnaire failed");
        
    }

    private boolean isCaseModel2(User user, String openId) {
        logger.info("into isCaseModel2");
        if (user != null && stringRedisTemplate.hasKey(OPENID + user.getOpenId() + INDI_INFIX)) {
            return true;
        } else if (user != null) {
            int count = indicatorService.count(new QueryWrapper<Indicator>()
                    .eq("open_id", openId)
            );
            
            if (count == 0) {
                return false;
            }

            Indicator indicator = indicatorMapper.selectOne(Wrappers.<Indicator>lambdaQuery().eq(Indicator::getOpenId, openId).last("limit 1"));
            
            logger.debug("indicator: {}", indicator.toString());
            if (null != indicator) {
                // TODO: 如果get不到??? 会不会异常??? 
                Optional<String> strOptional = Optional.ofNullable(indicator.getBiochemicalIndicatorsStr());
                if (strOptional.isPresent()) {
                    String str = strOptional.get();
                    logger.debug("indicator str: {}", str);
                    if (StringUtils.isNotBlank(str)) {
                        return true;
                    }
                }
            }
        } 
        
        return false;
    }

    private String serveValueStr(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

}
