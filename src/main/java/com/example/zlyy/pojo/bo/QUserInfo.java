package com.example.zlyy.pojo.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.zlyy.common.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@TableName("tb_")
// TODO: 大改
public class QUserInfo extends Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


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
