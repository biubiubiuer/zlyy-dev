package com.example.zlyy.entity;

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
@TableName("tb_question_c")
public class QuestionC extends Question implements Serializable {


    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Integer C01;
    private Integer C02;
    
    private Integer C0201;
    private Integer C0202;
    private Integer C0203;
    
    private Integer C03;
    
    private Integer C0301;
    
    private Integer C04;
    
    private Integer C0401;
    private Integer C0402;
    private Integer C0403;
    private Integer C0404;
    private Integer C0405;
    private Integer C0406;
    
    private Integer C05;
    private Integer C06;
    private Integer C07;
    private Integer C08;
    private Integer C09;
    private Integer C10;
    private Integer C11;
}
