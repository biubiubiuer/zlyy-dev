package com.example.zlyy.service;

public interface WxService {
    
    public String wxDecrypt(String excryptedData, String sessionId, String vi) throws Exception;
    
}
