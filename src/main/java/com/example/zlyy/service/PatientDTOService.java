package com.example.zlyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zlyy.dto.PatientDTO;
import com.example.zlyy.dto.R;

public interface PatientDTOService {
    
    boolean submitPatientInfo(PatientDTO patientDTO);
    
    R getPatientListByName();
    
}
