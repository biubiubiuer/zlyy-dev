package com.example.zlyy.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@TableName("tb_patient")
public class PatientDTO {
    
    private String name;
    private String sex;
    private String birthYear;
    private String nation;
    private String height;
    private String weight;
    private String bloodType;
    private String address;
    private String idCard;
    private String stmPoss;
    
    @JsonIgnore
    private String time;
    
}
