package com.example.zlyy.pojo.dto;

import com.example.zlyy.pojo.bo.WxUserInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    
    private Long id;

    private String nickname;

    private String username;

    @NonNull
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

    @NonNull
    private String phoneNumber;

    @JsonIgnore
    private String openId;

    @JsonIgnore
    private String wxUnionId;
    
    // dto 拓展属性
    private String token;
    List<String> permissions;
    List<String> roles;
    
    // 验证码
    private String code;
    
    public void from(WxUserInfo wxUserInfo) {
        this.nickname = wxUserInfo.getNickname();
        this.portrait = wxUserInfo.getAvatarUrl();
        this.username = "";
        this.password = "";
        this.phoneNumber = "";
        this.gender = wxUserInfo.getGender();
        this.openId = wxUserInfo.getOpenId();
        this.wxUnionId = wxUserInfo.getUnionId();
    }
    
    
}
