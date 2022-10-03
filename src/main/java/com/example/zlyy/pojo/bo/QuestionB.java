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
public class QuestionB extends Question implements Serializable {


    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    private String B01;

    private String B0201;
    private String B0202;
    private String B0203;
    private String B0204;
    private String B0205;
    private String B0206;
    
    private String B03;
    
    private String B0401;
    private String B0402;
    private String B0403;
    private String B0404;
    private String B0405;
    private String B0406;
    
    private String B05;
    
    
}
