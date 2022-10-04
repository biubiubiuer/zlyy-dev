package com.example.zlyy.service.impl;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.zlyy.service.WxService;
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

@Slf4j
@Service
public class WxServiceImpl implements WxService {
    
    Logger logger = LoggerFactory.getLogger(WxServiceImpl.class);
    
//    @Resource
//    private RedisMapper redisMapper;
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    @Override
    public String wxDecrypt(String excryptedData, String sessionId, String vi) throws Exception {
//        String json = redisMapper.get(WX_SESSION_ID + sessionId);
        String json = stringRedisTemplate.opsForValue().get(WX_SESSION_ID + sessionId);
        logger.debug("get session id: {}", json);
        JSONObject jsonObject = JSON.parseObject(json);
        logger.debug("session id to json obj: {}", jsonObject);
        String sessionKey = (String) jsonObject.get("session_key");
        
        try {
            byte[] encData = Base64.decodeBase64(excryptedData);
            logger.debug("encData: {}", encData);
            try {
                String result = "";
                byte[] iv = Base64.decodeBase64(vi);
                try {
                    byte[] key = Base64.decodeBase64(sessionKey);
                    try {

                        try {
                            // 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
                            int base = 16;
                            if (key.length % base != 0) {
                                int groups = key.length / base
                                        + (key.length % base != 0 ? 1 : 0);
                                byte[] temp = new byte[groups * base];
                                Arrays.fill(temp, (byte) 0);
                                System.arraycopy(key, 0, temp, 0, key.length);
                                key = temp;
                            }
                            // 初始化
                            Security.addProvider(new BouncyCastleProvider());
                            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
                            SecretKeySpec spec = new SecretKeySpec(key, "AES");
                            AlgorithmParameters parameters = AlgorithmParameters
                                    .getInstance("AES");
                            parameters.init(new IvParameterSpec(iv));
                            // 初始化
                            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
                            byte[] resultByte = cipher.doFinal(encData);
                            if (null != resultByte && resultByte.length > 0) {
                                result = new String(resultByte, "UTF-8");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        
                        
//                        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
//                        try {
//                            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
//                            logger.debug("cipher class: {}", cipher.getClass());
//                            try {
//                                SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); // TODO: 1004 这一行报错
//                                
//                                try {
//                                    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
//                                    
//                                    try {
//                                        return new String(cipher.doFinal(encData), "UTF_8");
//
//                                    } catch (Exception e) {
//                                        logger.error("new string failed: {}", e);
//                                        e.printStackTrace();
//                                    }
//
//
//                                } catch (Exception e) {
//                                    logger.error("cipher init failed: {}", e);
//                                    e.printStackTrace();
//                                }
//
//
//                            } catch (Exception e) {
//                                logger.error("new SecretKeySpec failed: {}", e);
//                                e.printStackTrace();
//                            }
//
//                        } catch (Exception e) {
//                            logger.error("new IvParameterSpec failed: {}", e);
//                            e.printStackTrace();
//                        }
                    } catch (Exception e) {
                        logger.error("after decode failed: {}", e);
                        e.printStackTrace();
                    }
                    
                } catch (Exception e) {
                    logger.error("decode session key failed: {}", e);
                    e.printStackTrace();
                }
            } catch (Exception e) {
                logger.error("decode iv failed: {}", e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            logger.error("decode encData failed: {}", e);
            e.printStackTrace();
        }
        
        return "error decoding";
        
    }
}
