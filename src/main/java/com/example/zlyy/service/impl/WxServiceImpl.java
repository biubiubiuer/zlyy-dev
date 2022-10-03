package com.example.zlyy.service.impl;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.zlyy.mapper.RedisMapper;
import com.example.zlyy.service.WxService;
import org.apache.tomcat.util.net.openssl.ciphers.Encryption;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.spec.AlgorithmParameterSpec;

import static com.example.zlyy.util.RedisConstants.WX_SESSION_ID;

@Service
public class WxServiceImpl implements WxService {
    
    @Resource
    private RedisMapper redisMapper;
    
    @Override
    public String wxDecrypt(String excryptedData, String sessionId, String vi) throws Exception {
        String json = redisMapper.get(WX_SESSION_ID + sessionId);
        JSONObject jsonObject = JSON.parseObject(json);
        String sessionKey = (String) jsonObject.get("session_key");
        
        byte[] encData = Base64.decode(excryptedData);
        byte[] iv = Base64.decode(vi);
        byte[] key = Base64.decode(sessionKey);
        
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCSSPadding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        
        return new String(cipher.doFinal(encData), "UTF-8");
    }
}
