package com.example.zlyy.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.zlyy.pojo.Patient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO implements Serializable {
    
    private Long id;
    
    private String name;
    private String sex;
    private String phoneNumber;
    private String birthYear;
    private String nation;
    private String height;
    private String weight;
    private String bloodType;
    private String address;
    private String idCard;
    private String stmPoss;
    
    // dto 拓展属性
    private List<Patient> patients;
    
    public void from(Patient patient) {
        this.name = patient.getName();
        this.sex = patient.getSex();
        this.phoneNumber = patient.getPhoneNumber();
        this.birthYear = patient.getBirthYear();
        this.nation = patient.getNation();
        this.height = patient.getHeight();
        this.weight = patient.getWeight();
        this.bloodType = patient.getBloodType();
        this.address = patient.getAddress();
        this.idCard = patient.getIdCard();
        this.stmPoss = patient.getStmPoss();
    }
    
}
