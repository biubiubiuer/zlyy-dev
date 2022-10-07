package com.example.zlyy.mapper;

import com.example.zlyy.pojo.Patient;

import java.util.List;
import java.util.Map;

public interface PatientMapper {
    boolean insert(Patient patient);
    
    List<Map<String, Object>> selectAllPatients();
    
}
