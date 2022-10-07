package com.example.zlyy.service;

import com.example.zlyy.util.JPair;

public interface WxService {
    
//    public JPair<? extends Object, ? extends Object> wxDecrypt(String excryptedData, String sessionId, String vi) throws Exception;
    String[] wxDecrypt(String excryptedData, String sessionId, String vi) throws Exception;

}
