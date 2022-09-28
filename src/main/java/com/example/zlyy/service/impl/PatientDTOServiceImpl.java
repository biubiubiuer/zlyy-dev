package com.example.zlyy.service.impl;

import com.example.zlyy.dto.PatientDTO;
import com.example.zlyy.dto.R;
import com.example.zlyy.mapper.PatientDTOMapper;
import com.example.zlyy.service.PatientDTOService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PatientDTOServiceImpl implements PatientDTOService {
    
    private static final Logger logger = LoggerFactory.getLogger(PatientDTOServiceImpl.class);
    
    @Resource
    PatientDTOMapper patientDTOMapper;

    @Transactional(rollbackFor= SQLException.class)
    @Override
    public boolean submitPatientInfo(PatientDTO patientDTO) {
        try {
            patientDTOMapper.insert(patientDTO);
            return true;
        } catch (Exception e) {
            logger.error("mysql insert error, {}", e);
            e.printStackTrace();
        }
        
        return false;
    }

    @Override
    public R getPatientListByName() {
        
        List<Map<String, Object>> maps = patientDTOMapper.selectAll();
        
        if (maps.size() == 0) {
            return R.error("病患列表为空!");
        }
        
        Map<String, Map<String, Object>> tmp = new HashMap<>();
        for (Map<String, Object> map : maps) {
            if (map.containsKey("name") && map.get("name") != null && !tmp.containsKey(map.get("name"))) {
                tmp.put((String) map.get("name"), map);
            }
        }

        List<Map<String, Object>> res = new ArrayList<>();

        tmp.entrySet().stream().forEach(e -> {
            res.add(e.getValue());
        });

        return R.ok().put("list", res);
        
    }
}
