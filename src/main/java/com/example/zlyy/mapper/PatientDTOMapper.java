package com.example.zlyy.mapper;

import com.example.zlyy.dto.PatientDTO;

import java.util.List;
import java.util.Map;

public interface PatientDTOMapper {
    boolean insert(PatientDTO patientDTO);

    List<Map<String, Object>> selectAll();
}
