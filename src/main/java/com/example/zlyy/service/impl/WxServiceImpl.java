package com.example.zlyy.service.impl;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.zlyy.service.WxService;
import com.example.zlyy.util.AESUtils;
import com.example.zlyy.util.JPair;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.AlgorithmParameters;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;


import org.apache.tomcat.util.codec.binary.Base64;

import static com.example.zlyy.util.RedisConstants.WX_SESSION_ID;
import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
public class WxServiceImpl implements WxService {
    
    Logger logger = LoggerFactory.getLogger(WxServiceImpl.class);
    
//    @Resource
//    private RedisMapper redisMapper;
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    
    
    
    
    //TODO: 解密算法拿不到openId
    @Override
    public String[] wxDecrypt(String excryptedData, String sessionId, String vi) throws Exception {
        
        excryptedData = excryptedData.replace(" ", "+");
        
        String json = stringRedisTemplate.opsForValue().get(WX_SESSION_ID + sessionId);
        logger.debug("get session id: {}", json);
        JSONObject jsonObject = JSON.parseObject(json);
        logger.debug("session id to json obj: {}", jsonObject);  // 包括了openId
        
        String openId = jsonObject.getString("openid");
        String unionId = jsonObject.getString("unionid");
        
        String sessionKey = (String) jsonObject.get("session_key");
        try {
            byte[] encData = cn.hutool.core.codec.Base64.decode(excryptedData);
            logger.debug("encData: {}", encData);
            byte[] iv = cn.hutool.core.codec.Base64.decode(vi);
            logger.debug("iv: {}", iv);
            byte[] key = cn.hutool.core.codec.Base64.decode(sessionKey);
            logger.debug("key: {}", key);
            try {
                AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
                try {
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    logger.debug("cipher class: {}", cipher.getClass());
                    try {
                        SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); // TODO: 1004 这一行报错
                        try {
                            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
                            return new String[] {new String(cipher.doFinal(encData), UTF_8), openId, unionId};
                        } catch (Exception e) {
                            logger.error("ipher init failed: {}", e);
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        logger.error("new SecretKeySpec failed: {}", e);
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    logger.error("Cipher.getInstance failed: {}", e);
                    e.printStackTrace();
                }
            } catch (Exception e) {
                logger.error("new IvParameterSpec failed: {}", e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            logger.error("after decode failed: {}", e);
            e.printStackTrace();
        }
        return null;
    }
}
