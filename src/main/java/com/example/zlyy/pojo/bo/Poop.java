package com.example.zlyy.pojo.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.zlyy.common.Question;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Poop extends Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String yanse;
    private String xingzhuang;
    private String nianye;
    private String bianxue;
    private String baixibao;
    private String hongxibao;
    private String nongxibao;
    private String tunshixibao;
    private String dianfenkeli;
    private String zhifangli;
    private String sijiaomujun;
    private String yinxue;
    private String qita;
}
