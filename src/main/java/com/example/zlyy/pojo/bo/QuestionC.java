package com.example.zlyy.pojo.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.zlyy.common.Question;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
public class QuestionC extends Question implements Serializable {


    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String C01;
    private String C02;
    
    private String C0201;
    private String C0202;
    private String C0203;
    
    private String C03;
    
    private String C0301;
    
    private String C04;
    
    private String C0401;
    private String C0402;
    private String C0403;
    private String C0404;
    private String C0405;
    private String C0406;
    
    private String C05;
    private String C06;
    private String C07;
    private String C08;
    private String C09;
    private String C10;
    private String C11;
}
