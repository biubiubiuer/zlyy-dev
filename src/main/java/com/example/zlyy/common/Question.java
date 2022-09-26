//package com.example.zlyy.entity;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.experimental.Accessors;
//
//import java.io.Serializable;
//
//@Data
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
//@TableName("tb_questionaire")
//public class Question implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 主键
//     */
//    @TableId(value = "id", type = IdType.AUTO)
//    private Long id;
//
//    /**
//     * 手机号, 通过云函数 getMobile 获得
//     * https://www.nhooo.com/note/qa314x.html
//     */
//    private String phoneNumber;
//
//    /**
//     * 问题类型
//     */
//    private Integer questionType;
//
//    /**
//     * 问题id
//     */
//    Long qid;
//
//    /**
//     * 问题名称
//     */
//    String title;
//
//    /**
//     * 选项
//     */
//    Integer option;
//
//    /**
//     * 选项数量
//     */
//    Integer optionNum;
//
//    /**
//     * 文本答案
//     * 为空时 option 为 -3
//     */
//    String answer;
//    
//}


package com.example.zlyy.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

public class Question {

}
