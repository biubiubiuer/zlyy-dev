package com.example.zlyy.util;

import com.example.zlyy.common.Question;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class QuestionUtils {
    
    

    public static void serveSingleQuestion(Question q, Integer[] inputList, Map<String, Integer> title2QidMap) {

        Logger logger = LoggerFactory.getLogger(q.getClass());
        
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
                        jsonKey2InputList(fieldName, o, intValue, q, inputList, title2QidMap);
                    } catch (Exception e) {
                        logger.error(
                                "entity get method error: {}, o: {}, o.toString(): {}, readMethod: {}, fieldName: {}, err: {}",
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


    public static void jsonKey2InputList(String fieldName, Object o, Integer intValue,
                                         Question q, Integer[] inputList, Map<String, Integer> title2QidMap) {
        
        Logger logger = LoggerFactory.getLogger(q.getClass());
        
        // TODO: Integer -> String 后, 这里的Integer.class.isAssignableFrom(o.getClass()) 也要改成String
        if (null != o && String.class.isAssignableFrom(o.getClass())) { 
//            String strValue = o.toString().replaceAll(" ", "");
            String strValue = QuestionUtils.removeSpace((String) o);
            logger.debug("strValue: {}", strValue);
            if (RegexUtils.isNumberValid(strValue)) {
                intValue = Integer.valueOf(strValue);
            }
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

    public static <T> T concatenate(T a, T b) {
        if (!a.getClass().isArray() || !b.getClass().isArray()) {
            throw new IllegalArgumentException();
        }
        Class<?> resCompType;
        Class<?> aCompType = a.getClass().getComponentType();
        Class<?> bCompType = b.getClass().getComponentType();
        if (aCompType.isAssignableFrom(bCompType)) {
            resCompType = aCompType;
        } else if (bCompType.isAssignableFrom(aCompType)) {
            resCompType = bCompType;
        } else {
            throw new IllegalArgumentException();
        }
        int aLen = Array.getLength(a);
        int bLen = Array.getLength(b);
        @SuppressWarnings("unchecked")
        T result = (T) Array.newInstance(resCompType, aLen + bLen);
        System.arraycopy(a, 0, result, 0, aLen);
        System.arraycopy(b, 0, result, aLen, bLen);
        return result;
    }
    
    public static String removeSpace(String s) {
        return s.replace(" ", "");
    }
    
}
