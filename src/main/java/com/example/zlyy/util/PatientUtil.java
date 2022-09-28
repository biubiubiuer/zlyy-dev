package com.example.zlyy.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.math3.analysis.function.Add;
import reactor.util.annotation.Nullable;

import java.util.List;
import java.util.Map;

public class PatientUtil {
    public static String parseNation(Integer nation) {
        switch (nation) {
            case 0: return "汉族";
            case 1: return "彝族";
            case 2: return "藏族";
            case 3: return "羌族";
            default: return "其他";
        }
    }

    public static String parseAddress(String address) {
        
        List<Map<String, String>> maps = AddressResolutionUtil.addressResolution(address);

        String province = null;
        String city = null;
        for (Map<String, String> map : maps) {
            if (StringUtils.isEmpty(province) && map.containsKey("province")) {
                province = map.get("province");
            }
            if (StringUtils.isEmpty(city) && map.containsKey("city")) {
                city = map.get("city");
            }
        }
        
        if (StringUtils.isNotEmpty(province) && StringUtils.isNotEmpty(city)) {
            return province + city;
        } else if (StringUtils.isNotEmpty(province)) {
            return province;
        } else if (StringUtils.isNotEmpty(city)) {
            return city;
        } 
        
        return "无";
    }

    public static String parseBloodType(Integer a04) {
        switch (a04) {
            case 1: return "O";
            case 2: return "A";
            case 3: return "B";
            case 4: return "AB";
            default: return "其他";
        }
    }
}
