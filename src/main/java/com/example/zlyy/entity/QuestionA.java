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
@TableName("tb_question_a")
public class QuestionA extends Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Integer A01;
    private Integer A02;
    private Integer A03;
    private Integer A04;
    private Integer A05;
    private Integer A06;
    private Integer A07;

}
