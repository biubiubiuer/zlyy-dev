package com.example.zlyy.pojo.bo;

import lombok.*;

import java.io.Serializable;

//@Data
//@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString()
public class WXAuth {
    private String excryptedData;
    private String iv;
    private String sessionId;

    @Override
    public String toString() {
        return "WXAuth{" +
                "excryptedData='" + excryptedData + '\'' +
                ", iv='" + iv + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
