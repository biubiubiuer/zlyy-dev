package com.example.zlyy.pojo.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WXAuth {
    private String excryptedData;
    private String iv;
    private String sessionId;
}
