package com.example.zlyy.util;

import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MapBuilder {

    private static final Logger logger = LoggerFactory.getLogger(MapBuilder.class);
    
    public static Map<String, ? extends Object> build(String[] o1, Object[] o2) throws Exception {
        
        logger.debug("o1.length = " + o1.length + ", o2.length = " + o2.length);
        
        if (o1 == null || o1.length == 0 || o2 == null || o2.length == 0) {
            return null;
        }
        if (o1.length != o2.length) {
            throw new Exception("o1 and o2 must have the same length");
        }
        
        return new HashMap<String, Object>() {
            {
                for (int i = 0; i < o1.length; i++) {
                    put(o1[i], o2[i]);
                }
            }
        };
    }
}
