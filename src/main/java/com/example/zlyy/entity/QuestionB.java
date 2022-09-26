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
@TableName("tb_question_b")
public class QuestionB extends Question implements Serializable {


    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    private Integer B01;

    private Integer B0201;
    private Integer B0202;
    private Integer B0203;
    private Integer B0204;
    private Integer B0205;
    private Integer B0206;
    
    private Integer B03;
    
    private Integer B0401;
    private Integer B0402;
    private Integer B0403;
    private Integer B0404;
    private Integer B0405;
    private Integer B0406;
    
    private Integer B05;
    
    
}
