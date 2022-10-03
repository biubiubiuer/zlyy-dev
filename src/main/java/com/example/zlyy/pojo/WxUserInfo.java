package com.example.zlyy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class WxUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String openId;
    
    private String nickname;
    
    private String gender;
    
    private String city;
    
    private String province;
    
    private String country;
    
    private String avatarUrl;
    
    private String unionId;
    
}
