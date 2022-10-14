package com.example.zlyy.service.impl;

import com.example.zlyy.mapper.PatientMapper;
import com.example.zlyy.pojo.Indicator;
import com.example.zlyy.pojo.Patient;
import com.example.zlyy.common.R;
import com.example.zlyy.service.PatientService;
import com.example.zlyy.util.JPair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.alibaba.fastjson.JSONPatch.OperationType.add;

@Slf4j
@Service
public class PatientServiceImpl implements PatientService {
    
    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);
    
    @Resource
    PatientMapper patientMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    @Transactional(rollbackFor= SQLException.class)
    @Override
    public boolean submitPatientInfo(Patient patient, String time) {
        try {
            patient.setTime(time);
            patientMapper.insert(patient);
            return true;
        } catch (Exception e) {
            logger.error("mysql insert error, {}", e);
            e.printStackTrace();
        }
        
        return false;
    }

    @Override
    public R getPatientListByName() {
        
        List<Map<String, Object>> maps = patientMapper.selectAllPatients();

        if (maps.size() == 0) {
            return R.error("病患列表为空!");
        }

        return R.ok().put("list", maps);
        
    }

    @Override
    public R getWeekPatients() {

        List<Map<String, Object>> maps = patientMapper.selectAllPatients();
        
        if (maps.size() == 0) {
            return R.error("病患列表为空!");
        }
        
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        for (int i = 7; i >= 1; i--) {
            Date date = DateUtils.addDays(new Date(), -i);
            String formatDate = sdf.format(date);
            dateList.add(formatDate);
        }

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<Patient> patientList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            Patient patient = new Patient();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                switch (key) {
                    case "name": patient.setName((String) entry.getValue()); break;
                    case "sex": patient.setSex((String) entry.getValue()); break;
                    case "phone_number": patient.setPhoneNumber((String) entry.getValue()); break;
                    case "birth_year": patient.setBirthYear((String) entry.getValue()); break;
                    case "nation": patient.setNation((String) entry.getValue()); break;
                    case "height": patient.setHeight((String) entry.getValue()); break;
                    case "weight": patient.setWeight((String) entry.getValue()); break;
                    case "blood_type": patient.setBloodType((String) entry.getValue()); break;
                    case "address": patient.setAddress((String) entry.getValue()); break;
                    case "id_card": patient.setIdCard((String) entry.getValue()); break;
                    case "stm_poss": patient.setStmPoss((String) entry.getValue()); break;
                    case "time": patient.setTime(df.format((LocalDateTime) entry.getValue()).substring(5, 10)); break;
                    default: break;
                }
            }
            patientList.add(patient);
        }
        
        List<Integer> value = new ArrayList<>();
        
        for (int i = 0; i < dateList.size(); i++) {
            int val = 0;
            for (Patient patient : patientList) {
                if (patient.getTime().equals(dateList.get(i))) {
                    val++;
                }
            }
            value.add(val);
        }
        
        for (int i = 0; i < dateList.size(); i++) {
            dateList.set(i, dateList.get(i).substring(3, 5));
        }
        
        return R.ok().put("value", value).put("name", dateList);

    }

    @Override
    public R getMonthPatients() {

        List<Map<String, Object>> maps = patientMapper.selectAllPatients();

        if (maps.size() == 0) {
            return R.error("病患列表为空!");
        }

        List<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        for (int i = 28; i >= 1; i--) {
            Date date = DateUtils.addDays(new Date(), -i);
            String formatDate = sdf.format(date);
            dateList.add(formatDate);
        }

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<Patient> patientList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            Patient patient = new Patient();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                switch (key) {
                    case "name": patient.setName((String) entry.getValue()); break;
                    case "sex": patient.setSex((String) entry.getValue()); break;
                    case "phone_number": patient.setPhoneNumber((String) entry.getValue()); break;
                    case "birth_year": patient.setBirthYear((String) entry.getValue()); break;
                    case "nation": patient.setNation((String) entry.getValue()); break;
                    case "height": patient.setHeight((String) entry.getValue()); break;
                    case "weight": patient.setWeight((String) entry.getValue()); break;
                    case "blood_type": patient.setBloodType((String) entry.getValue()); break;
                    case "address": patient.setAddress((String) entry.getValue()); break;
                    case "id_card": patient.setIdCard((String) entry.getValue()); break;
                    case "stm_poss": patient.setStmPoss((String) entry.getValue()); break;
                    case "time": patient.setTime(df.format((LocalDateTime) entry.getValue()).substring(5, 10)); break;
                    default: break;
                }
            }
            patientList.add(patient);
        }

        List<Integer> value = new ArrayList<>();
        
        for (int i = 0; i < 4; i++) {
            int val = 0;
            for (Patient patient : patientList) {
                for (int j = 0; j < 7; j++) {
                    if (patient.getTime().equals(dateList.get(7 * i + j))) {
                        val++;
                    }
                }
            }
            value.add(val);
        }
        
        List<String> weekList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            weekList.add(dateList.get(7 * i).substring(0, 2) + "月" + dateList.get(7 * i).substring(3, 5) + "日-" + 
                    dateList.get(7 * i + 6).substring(0, 2) + "月" + dateList.get(7 * i + 6).substring(3, 5) + "日");
        }

        return R.ok().put("value", value).put("name", weekList);
    }

    @Override
    public R getYearPatients() {

        List<Map<String, Object>> maps = patientMapper.selectAllPatients();

        if (maps.size() == 0) {
            return R.error("病患列表为空!");
        }

        List<String> monthList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        //近六个月
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1); //要先+1,才能把本月的算进去
        for(int i=0; i<12; i++){
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1); //逐次往前推1个月
            monthList.add(String.valueOf(cal.get(Calendar.YEAR))
                    +"-"+ (cal.get(Calendar.MONTH)+1 < 10 ? "0" +
                    (cal.get(Calendar.MONTH)+1) : (cal.get(Calendar.MONTH)+1)));
        }

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<Patient> patientList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            Patient patient = new Patient();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                switch (key) {
                    case "name":
                        patient.setName((String) entry.getValue());
                        break;
                    case "sex":
                        patient.setSex((String) entry.getValue());
                        break;
                    case "phone_number":
                        patient.setPhoneNumber((String) entry.getValue());
                        break;
                    case "birth_year":
                        patient.setBirthYear((String) entry.getValue());
                        break;
                    case "nation":
                        patient.setNation((String) entry.getValue());
                        break;
                    case "height":
                        patient.setHeight((String) entry.getValue());
                        break;
                    case "weight":
                        patient.setWeight((String) entry.getValue());
                        break;
                    case "blood_type":
                        patient.setBloodType((String) entry.getValue());
                        break;
                    case "address":
                        patient.setAddress((String) entry.getValue());
                        break;
                    case "id_card":
                        patient.setIdCard((String) entry.getValue());
                        break;
                    case "stm_poss":
                        patient.setStmPoss((String) entry.getValue());
                        break;
                    case "time":
                        patient.setTime(df.format((LocalDateTime) entry.getValue()).substring(0, 7));
                        break;
                    default:
                        break;
                }
            }
            patientList.add(patient);
        }

        List<Integer> value = new ArrayList<>();
        
        for (int i = monthList.size() - 1; i >= 0; i--) {
            int val = 0;
            for (Patient patient : patientList) {
                if (patient.getTime().equals(monthList.get(i))) {
                    val++;
                }
            }
            value.add(val);
        }
        
        
        Collections.reverse(monthList);
        
        for (int i = 0; i < monthList.size(); i++) {
            String str = monthList.get(i).substring(5, 7);
            monthList.set(i, str.charAt(0) == '0' ? str.substring(1, 2) : str);
        }
        
        int start = 0;
        for (int i = 0; i < monthList.size(); i++) {
            if ("1".equals(monthList.get(i))) {
                start = i;
            }
        }
        
        List<String> newStringList = new ArrayList<>();
        List<Integer> newIntList = new ArrayList<>();
        for (int i = start; i < 12; i++) {
            newIntList.add(value.get(i));
            newStringList.add(monthList.get(i));
        }
        for (int i = 0; i < start; i++) {
            newIntList.add(value.get(i));
            newStringList.add(monthList.get(i));
        }
        

//        List<String> monthList = new ArrayList<>();
//        for (int i = 0; i < 12; i++) {
//            monthList.add(String.valueOf(i));
//        }
//        List<Integer> value = new ArrayList<>();
//        for (int i = 0; i < 12; i++) {
//            value.add(i);
//        }

        return R.ok().put("value", newIntList).put("name", newStringList);
        
//        return R.ok().put("value", value).put("name", monthList);
    }
}
