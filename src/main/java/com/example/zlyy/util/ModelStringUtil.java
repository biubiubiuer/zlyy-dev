package com.example.zlyy.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class ModelStringUtil {

    private static final Logger logger = LoggerFactory.getLogger(ModelStringUtil.class);
    
    public static String parseModelString(String s) {
        return removeInvalidCharacters(s);
    }

    private static String removeInvalidCharacters(String s) {
        String s1 = s.replace(" ", "");
        String s2 = s1.replace("{", "");
        String s3 = s2.replace("}", "");
        s3 += ",";
        String[] split = s3.split(",");
        String sr = null;
        try {
            sr = split[1];
        } catch (Exception e) {
            logger.error("model string split failed, illegal index error: {}", e.getMessage());
            e.printStackTrace();
        }
        
        String res = null;
        try {
            res = sr.substring(2);
        } catch (Exception e) {
            logger.error("sub str failed, illegal index error: {}", e.getMessage());
            e.printStackTrace();
        }
        
        return res;
    }
}
