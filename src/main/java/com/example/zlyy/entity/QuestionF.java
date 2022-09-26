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
@TableName("tb_question_f")
public class QuestionF extends Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer F01;
    private Integer F0102;
    private Integer F02;
    private Integer F0201;
    private Integer F0202;
    private Integer F03;
}
