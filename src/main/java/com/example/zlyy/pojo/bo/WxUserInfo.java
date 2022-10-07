package com.example.zlyy.pojo.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString()
public class WxUserInfo {
    
    private String openId;
    
    private String nickname;
    
    private String gender;
    
    private String city;
    
    private String province;
    
    private String country;
    
    private String avatarUrl;
    
    private String unionId;
    
}
