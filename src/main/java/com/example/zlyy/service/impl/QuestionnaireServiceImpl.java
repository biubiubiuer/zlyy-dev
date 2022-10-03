package com.example.zlyy.service.impl;

import com.example.zlyy.handler.UserThreadLocal;
import com.example.zlyy.mapper.PatientMapper;
import com.example.zlyy.mapper.RedisMapper;
import com.example.zlyy.pojo.bo.*;
import com.example.zlyy.common.R;
import com.example.zlyy.pojo.dto.UserDTO;
import com.example.zlyy.service.QuestionnaireService;
import com.example.zlyy.util.ClassificationModel;
import com.example.zlyy.util.JWTUtils;
import com.example.zlyy.util.MyMapBuilder;
import com.example.zlyy.util.QuestionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.dmg.pmml.FieldName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.zlyy.util.ArrayConstants.QUES_KEYS;
import static com.example.zlyy.util.ArrayConstants.QUES_VALUES;
import static com.example.zlyy.util.QuestionUtils.jsonKey2InputList;
import static com.example.zlyy.util.QuestionUtils.serveSingleQuestion;
import static com.example.zlyy.util.RedisConstants.REDIS_INFIX;
import static com.example.zlyy.util.RedisConstants.TOKEN_KEY;
import static com.example.zlyy.util.StringConstants.PMML_PATH;
import static com.example.zlyy.util.StringConstants.PMML_PATH_1;

@Slf4j
@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionnaireServiceImpl.class);
    
    @Resource
    private PatientMapper patientMapper;
    
    @Resource
    private RedisMapper redisMapper;
    
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
            QUserInfo QUserInfo,
            QuestionA questionA, QuestionB questionB, QuestionC questionC,
            QuestionD questionD, QuestionE questionE, QuestionF questionF,
            MultiOptionQuestion multiOptionQuestion
    ) {


        // userInfo
        List<Field> fields1 = Arrays.stream(QUserInfo.getClass().getDeclaredFields()).filter(f -> {
            String name = f.getName();
            return !"id".equals(name) && !"serialVersionUID".equals(name);
        }).collect(Collectors.toList());
        
        
        for (Field field : fields1) {
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), QUserInfo.getClass());
                Method readMethod = descriptor.getReadMethod();
                String fieldName = descriptor.getName();
                
                try {
                    Object o = readMethod.invoke(QUserInfo);
                    if ("sex".equals(fieldName) || "birthYear".equals(fieldName) || "durable".equals(fieldName)) {
                        int intValue;
                        if ("sex".equals(fieldName)) {
                            intValue = 0;
                        } else if ("birthYear".equals(fieldName)) {
                            intValue = 1980;
                        } else {
                            intValue = 60;
                        }
                        jsonKey2InputList(fieldName, o, intValue, QUserInfo, inputList, title2QidMap);
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
        
        // model
//        ClassificationModel clf = new ClassificationModel(PMML_PATH);
//        Map<FieldName, Number> waitPreSample = new HashMap<FieldName, Number>(inputList.length) {
//            {
//                for (int i = 0; i < inputList.length; i++) {
//                    put(new FieldName(String.valueOf(i)), inputList[i]);
//                }
//            }
//        };


//        String substring = clf.predictProba(waitPreSample).toString().substring(3, 9);
//        logger.debug(substring);
//        Double doubleModelRes = Double.valueOf(substring);
//        
//        doubleModelRes = (double) Math.round(doubleModelRes * 10000000) / 10000000;
//        logger.debug("strModelRes: {}", doubleModelRes);


//        Double finalDoubleModelRes = doubleModelRes;  // TODO: 包装类留下的坑? 这行应该可以删
        
        // TODO: insert to mysql 可以省略这步, 只存到redis
        // TODO: 10.1凌晨更正: 还是别存redis了
        // TODO: 10.3 先试试redis
//        String toDbStr = StringUtils.join(inputList, ",");

//        patientMapper.insertDbStr(toDbStr);
        
        
        // TODO: 结合模型2
        try {
            UserDTO userDTO = UserThreadLocal.get();
            String token = JWTUtils.sign(userDTO.getId());

            if (StringUtils.isNotBlank(token) && redisMapper.keyIsExists(TOKEN_KEY + token + REDIS_INFIX[0])) {

                String biochemicalIndicatorsStr = redisMapper.get(TOKEN_KEY + token + REDIS_INFIX[0]);
                biochemicalIndicatorsStr += ",";
                String[] split = biochemicalIndicatorsStr.split(",");
                int[] array = Arrays.asList(split).stream().mapToInt(Integer::parseInt).toArray();

                // 两个数组拼接
                Integer[] concatInputList = (Integer[]) QuestionUtils.concatenate(inputList, array);

                ClassificationModel clf = new ClassificationModel(PMML_PATH_1);
                Map<FieldName, Number> waitPreSample = new HashMap<FieldName, Number>() {
                    {
                        for (int i = 0; i < concatInputList.length; i++) {
                            put(new FieldName(String.valueOf(i)), concatInputList[i]);
                        }
                    }
                };

                String substring = clf.predictProba(waitPreSample).toString().substring(3, 9);
                logger.debug(substring);
                Double doubleModelRes = Double.valueOf(substring);

                doubleModelRes = (double) Math.round(doubleModelRes * 10000000) / 10000000;
                logger.debug("strModelRes: {}", doubleModelRes);

                return R.ok().put("modelRes", doubleModelRes);

            } else {

                ClassificationModel clf = new ClassificationModel(PMML_PATH);
                Map<FieldName, Number> waitPreSample = new HashMap<FieldName, Number>(inputList.length) {
                    {
                        for (int i = 0; i < inputList.length; i++) {
                            put(new FieldName(String.valueOf(i)), inputList[i]);
                        }
                    }
                };

                String substring = clf.predictProba(waitPreSample).toString().substring(3, 9);
                logger.debug(substring);
                Double doubleModelRes = Double.valueOf(substring);

                doubleModelRes = (double) Math.round(doubleModelRes * 10000000) / 10000000;
                logger.debug("strModelRes: {}", doubleModelRes);



                return R.ok().put("modelRes", doubleModelRes);
            }
            
        } catch (Exception e) {
            logger.error("userDTO get error, {}", e.getMessage());
        }

//        if (StringUtils.isNotBlank(token) && redisMapper.keyIsExists(TOKEN_KEY + token + REDIS_INFIX[0])) {
//
//            String biochemicalIndicatorsStr = redisMapper.get(TOKEN_KEY + token + REDIS_INFIX[0]);
//            biochemicalIndicatorsStr += ",";
//            String[] split = biochemicalIndicatorsStr.split(",");
//            int[] array = Arrays.asList(split).stream().mapToInt(Integer::parseInt).toArray();
//            
//            // 两个数组拼接
//            Integer[] concatInputList = (Integer[]) QuestionUtils.concatenate(inputList, array);
//
//            ClassificationModel clf = new ClassificationModel(PMML_PATH_1);
//            Map<FieldName, Number> waitPreSample = new HashMap<FieldName, Number>() {
//                {
//                    for (int i = 0; i < concatInputList.length; i++) {
//                        put(new FieldName(String.valueOf(i)), concatInputList[i]);
//                    }
//                }
//            };
//
//            String substring = clf.predictProba(waitPreSample).toString().substring(3, 9);
//            logger.debug(substring);
//            Double doubleModelRes = Double.valueOf(substring);
//
//            doubleModelRes = (double) Math.round(doubleModelRes * 10000000) / 10000000;
//            logger.debug("strModelRes: {}", doubleModelRes);
//
//            return R.ok().put("modelRes", doubleModelRes);
//            
//        } else {
//
//            ClassificationModel clf = new ClassificationModel(PMML_PATH);
//            Map<FieldName, Number> waitPreSample = new HashMap<FieldName, Number>(inputList.length) {
//                {
//                    for (int i = 0; i < inputList.length; i++) {
//                        put(new FieldName(String.valueOf(i)), inputList[i]);
//                    }
//                }
//            };
//
//            String substring = clf.predictProba(waitPreSample).toString().substring(3, 9);
//            logger.debug(substring);
//            Double doubleModelRes = Double.valueOf(substring);
//
//            doubleModelRes = (double) Math.round(doubleModelRes * 10000000) / 10000000;
//            logger.debug("strModelRes: {}", doubleModelRes);
//
//
//
//            return R.ok().put("modelRes", doubleModelRes);
//        }

        return R.error("userDTO get error");
        
    }

    private String serveValueStr(String str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

//    private void serveSingleQuestion(Question q) {
//        List<Field> fields2 = Arrays.stream(q.getClass().getDeclaredFields()).filter(f -> {
//            String name = f.getName();
//            return !"id".equals(name) && !"serialVersionUID".equals(name);
//        }).collect(Collectors.toList());
//
//        for (Field field : fields2) {
//            try {
//                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), q.getClass());
//                Method readMethod = descriptor.getReadMethod();
//                String fieldName = descriptor.getName();
//
//
//                try {
//                    Object o = readMethod.invoke(q);
//                    
////                    String type = o.getClass().toString();
////                    logger.debug("field type: {}", type);
//                    
//                    // mapping
//                    try {
//                        Integer intValue = -3;
//                        jsonKey2InputList(fieldName, o, intValue);
//                    } catch (Exception e) {
//                        logger.error(
//                                "entity get method error: {}, o: {}, o.toString(): {}, readMethod: {}, fieldName: {}, err: {}", 
//                                e.getMessage(), 
//                                o, 
//                                o.toString(), 
//                                readMethod, 
//                                fieldName, 
//                                e
//                        );
//                        e.printStackTrace();
//                    }
//                } catch (InvocationTargetException e) {
//                    logger.error("invoke error: {}" + e.getMessage(), e);
//                    e.printStackTrace();
//                }
//
//
//            } catch (IntrospectionException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    private void jsonKey2InputList(String fieldName, Object o, Integer intValue) {
//        if (o != null && Integer.class.isAssignableFrom(o.getClass())) {
//            intValue = Integer.valueOf(o.toString());
//        }
//        try {
//            int indexValue = title2QidMap.get(fieldName);
//            try {
//                inputList[indexValue] = intValue;
//            } catch (IllegalArgumentException e) {
//                logger.error("illegal index error: {}" + e.getMessage(), e);
//                e.printStackTrace();
//            }
//        } catch (IllegalArgumentException e) {
//            logger.error("hashmap get error: {}" + e.getMessage(), e);
//            e.printStackTrace();
//        }
//    }

}
