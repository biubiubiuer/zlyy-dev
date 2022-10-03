package com.example.zlyy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String nickname;
    
    private String username;
    
    private String password;
    
    private String gender;

    /**
     * 头像
     */
    private String portrait;

    /**
     * 背景图片
     */
    private String background;
    
    private String phoneNumber;
    
    private String openId;
    
    private String wxUnionId;
    
    
}
