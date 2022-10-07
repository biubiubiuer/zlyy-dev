package com.example.zlyy.service;

import com.example.zlyy.pojo.Patient;
import com.example.zlyy.common.R;

public interface PatientService {
    
    boolean submitPatientInfo(Patient patient, String time);
    
    R getPatientListByName();

    R getWeekPatients();

    R getMonthPatients();

    R getYearPatients();
}
