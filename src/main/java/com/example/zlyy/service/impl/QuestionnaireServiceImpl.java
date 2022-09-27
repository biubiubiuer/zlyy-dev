package com.example.zlyy.service.impl;

import com.example.zlyy.common.Question;
import com.example.zlyy.dto.R;
import com.example.zlyy.entity.*;
import com.example.zlyy.service.QuestionnaireService;
import com.example.zlyy.util.ClassificationModel;
import com.example.zlyy.util.MapBuilder;
import lombok.extern.slf4j.Slf4j;
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

import static com.example.zlyy.util.StringConstants.PMML_PATH;

@Slf4j
@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionnaireServiceImpl.class);
    
    Integer[] inputList = initializeInputList();

    private Integer[] initializeInputList() {
        Integer[] in = new Integer[109];
        Arrays.fill(in, -3);
        return in;
    }

    String[] keys = new String[] {
            "sex", "birthYear", "durable", 
            "A01", "A02", "A03", "A04", "A05", "A06", "A07", 
            "B01", "B0201", "B0202", "B0203", "B0204", "B0205", "B0206", "B03", "B0401", "B0402", "B0403", "B0404", "B0405", "B0406", "B05", 
            "C01", "C02", "C0201", "C0202", "C0203", "C03", "C0301", "C04", "C0401", "C0402", "C0403", "C0404", "C0405", "C0406", "C05", "C06", "C07", "C08", "C09", "C10", "C11",
            "D01", "D0201", "D0202Map", "D03", "D04Map", 
            "E01", "E0101Map", "E0102Map", "E02", "E0201Map", "E0202Map",
            "F01", "F0101Map", "F0102", "F02", "F0201", "F0202", "F03"
    };
    
    Integer[] values = new Integer[] {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 
            33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 58, 59, 64, 65, 71, 79, 80, 86, 96, 97, 
            104, 105, 106, 107, 108
    };

    
    
    private Map<String, Integer> title2QidMap;

    {
        try {
            title2QidMap = (Map<String, Integer>) MapBuilder.build(keys, values);
        } catch (Exception e) {
            logger.error("map build error: {}", e);
            e.printStackTrace();
        }
    }


    @Override
    public R updateQuestionnaire(
            UserInfo userInfo, 
            QuestionA questionA, QuestionB questionB, QuestionC questionC, 
            QuestionD questionD, QuestionE questionE, QuestionF questionF, 
            MultiOptionQuestion multiOptionQuestion
    ) {


        // userInfo
        List<Field> fields1 = Arrays.stream(userInfo.getClass().getDeclaredFields()).filter(f -> {
            String name = f.getName();
            return !"id".equals(name) && !"serialVersionUID".equals(name);
        }).collect(Collectors.toList());
        
        
        for (Field field : fields1) {
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), userInfo.getClass());
                Method readMethod = descriptor.getReadMethod();
                String fieldName = descriptor.getName();
                
                try {
                    Object o = readMethod.invoke(userInfo);
                    if ("sex".equals(fieldName) || "birthYear".equals(fieldName) || "durable".equals(fieldName)) {
                        Integer intValue;
                        if ("sex".equals(fieldName)) {
                            intValue = 0;
                        } else if ("birthYear".equals(fieldName)) {
                            intValue = 1980;
                        } else {
                            intValue = 60;
                        }
                        if (o != null && Integer.class.isAssignableFrom(o.getClass())) {
                            intValue = Integer.valueOf(o.toString());
                        }
                        try {
                            int indexValue = title2QidMap.get(fieldName);
                            try {
                                inputList[indexValue] = intValue;
                            } catch (IllegalArgumentException e) {
                                logger.error("illegal index error: {}" + e.getMessage(), e);
                                e.printStackTrace();
                            }
                        } catch (IllegalArgumentException e) {
                            logger.error("hashmap get error: {}" + e.getMessage(), e);
                            e.printStackTrace();
                        }
                    }
                } catch (InvocationTargetException e) {
                    logger.error("invoke error: {}" + e.getMessage(), e);
                    e.printStackTrace();
                }
                
                
                 
            } catch (IntrospectionException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        // TODO: bug1: 部分实参列表引用; bug2: checkbox的hashmap的 key
        
        // A B C D E F
        // 反射获取属性列表
        
        serveSingleQuestion(questionA);
        serveSingleQuestion(questionB);
        serveSingleQuestion(questionC);
        serveSingleQuestion(questionD);
        serveSingleQuestion(questionD);
        serveSingleQuestion(questionE);
        serveSingleQuestion(questionF);
        
        
        
        
        
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
                if (o != null && o.containsKey("options")) {
                    Integer optionNum = (Integer) o.get("options");
                    // check-box mapping
                    int[] intValues = new int[optionNum];
                    for (int i = 0; i < intValues.length; i++) {
                        intValues[i] = -3;
                    }
                    if (o.containsKey("value")) {
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
        ClassificationModel clf = new ClassificationModel(PMML_PATH);
        List<String> featureNames = clf.getFeatureNames();
        Map<FieldName, Number> waitPreSample = new HashMap<FieldName, Number>() {
            {
                for (int i = 0; i < inputList.length; i++) {
                    put(new FieldName(String.valueOf(i)), inputList[i]);
                }
            }
        };


        String substring = clf.predictProba(waitPreSample).toString().substring(3, 9);
        logger.debug(substring);
        Double doubleModelRes = Double.valueOf(substring);
        
        doubleModelRes = (double) Math.round(doubleModelRes * 1000000) / 1000000; 
        logger.debug("strModelRes: {}", doubleModelRes.toString());


        Double finalDoubleModelRes = doubleModelRes;
        
        return R.ok().put("modelRes", doubleModelRes);
        
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

    private void serveSingleQuestion(Question q) {
        List<Field> fields2 = Arrays.stream(q.getClass().getDeclaredFields()).filter(f -> {
            String name = f.getName();
            return !"id".equals(name) && !"serialVersionUID".equals(name);
        }).collect(Collectors.toList());

        for (Field field : fields2) {
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), q.getClass());
                Method readMethod = descriptor.getReadMethod();
                String fieldName = descriptor.getName();


                try {
                    Object o = readMethod.invoke(q);
                    
//                    String type = o.getClass().toString();
//                    logger.debug("field type: {}", type);
                    
                    // mapping
                    try {
                        Integer intValue = -3;
                        if (o != null && Integer.class.isAssignableFrom(o.getClass())) {
                            intValue = Integer.valueOf(o.toString());
                        }
                        try {
                            int indexValue = title2QidMap.get(fieldName);
                            try {
                                inputList[indexValue] = intValue;
                            } catch (IllegalArgumentException e) {
                                logger.error("illegal index error: {}" + e.getMessage(), e);
                                e.printStackTrace();
                            }
                        } catch (IllegalArgumentException e) {
                            logger.error("hashmap get error: {}" + e.getMessage(), e);
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        logger.error(
                                "entity get method error: {}, o: {}, o.toString(): {}, readMethod: {}, fieldName: {}, err: {}" + e.getMessage(), 
                                e.getMessage(), 
                                o, 
                                o.toString(), 
                                readMethod, 
                                fieldName, 
                                e
                        );
                        e.printStackTrace();
                    }
                } catch (InvocationTargetException e) {
                    logger.error("invoke error: {}" + e.getMessage(), e);
                    e.printStackTrace();
                }


            } catch (IntrospectionException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
