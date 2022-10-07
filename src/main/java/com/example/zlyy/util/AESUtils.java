package com.example.zlyy.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;


/**
 * @ClassName AESUtils
 * @Description TODO
 * @Author 独狼Kiss
 * @Date 2021/9/14 13:46
 * @Version 1.0
 **/

@Slf4j
public class AESUtils {
//    
//    private static final Logger logger = LoggerFactory.getLogger(AESUtils.class);
//    
//    @Resource
//    private static StringRedisTemplate stringRedisTemplate;
//    
//    
//    // 加密模式
//    private static final String ALGORITHM = "AES/CBC/PKCS7Padding";
//    private static final String CHARSET_NAME = "UTF-8";
//    private static final String AES_NAME = "AES";
//
//    //解决java.security.NoSuchAlgorithmException: Cannot find any provider supporting AES/CBC/PKCS7Padding
//    static {
//        Security.addProvider(new BouncyCastleProvider());
//    }
//
//    /**
//     * 解密
//     *
//     * @param content 目标密文
//     * @param key     秘钥
//     * @param iv      偏移量
//     * @return
//     */
//    public static String decrypt(@NotNull String content, @NotNull String key, @NotNull String iv) {
//        String result = "";
//        
//        
////        String json = stringRedisTemplate.opsForValue().get(WX_SESSION_ID + key); //TODO: 空指针异常
////        logger.debug("get session id: {}", json);
////        JSONObject jsonObject = JSON.parseObject(json);
////        logger.debug("session id to json obj: {}", jsonObject);
////        String sessionKey = (String) jsonObject.get("session_key");
//        
//        
//        // 被加密的数据
//        byte[] dataByte = Base64.decodeBase64(content);
//        logger.debug("dataByte: {}", dataByte.toString());
//        // 加密秘钥
////        byte[] keyByte = Base64.decodeBase64(sessionKey);
//        byte[] keyByte = Base64.decodeBase64(key);
//        logger.debug("dataByte: {}", dataByte.toString());
//        // 偏移量
//        byte[] ivByte = Base64.decodeBase64(iv);
//        logger.debug("ivByte: {}", ivByte.toString());
//
//        
//        try {
//            // 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
//            int base = 16;
//            if (keyByte.length % base != 0) {
//                int groups = keyByte.length / base
//                        + (keyByte.length % base != 0 ? 1 : 0);
//                byte[] temp = new byte[groups * base];
//                Arrays.fill(temp, (byte) 0);
//                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
//                keyByte = temp;
//            }
//            if (ivByte.length % base != 0) {
//                int groups = ivByte.length / base
//                        + (ivByte.length % base != 0 ? 1 : 0);
//                byte[] temp = new byte[groups * base];
//                Arrays.fill(temp, (byte) 0);
//                System.arraycopy(ivByte, 0, temp, 0, ivByte.length);
//                ivByte = temp;
//            }
//            // 初始化
//            Security.addProvider(new BouncyCastleProvider());
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
//            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
//            AlgorithmParameters parameters = AlgorithmParameters
//                    .getInstance("AES");
//            parameters.init(new IvParameterSpec(ivByte));
//            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
//            byte[] resultByte = cipher.doFinal(dataByte);
//            if (null != resultByte && resultByte.length > 0) {
//                result = new String(resultByte, "UTF-8");
//            }
//        } catch (NoSuchAlgorithmException e) {
//            logger.error("NoSuchPaddingException");
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            logger.error("NoSuchPaddingException");
//            e.printStackTrace();
//        } catch (InvalidParameterSpecException e) {
//            logger.error("InvalidParameterSpecException");
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            logger.error("IllegalBlockSizeException");
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            logger.error("BadPaddingException");
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            logger.error("UnsupportedEncodingException");
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            logger.error("InvalidKeyException");
//            e.printStackTrace();
//        } catch (InvalidAlgorithmParameterException e) {
//            logger.error("InvalidAlgorithmParameterException");
//            e.printStackTrace();
//        } catch (NoSuchProviderException e) {
//            logger.error("NoSuchProviderException");
//            e.printStackTrace();
//        }
//        return result;
//    }




    private static String decryptNew(String encryptedData, String sessionKey, String iv) throws Exception {
        String result = "";
        // 被加密的数据
        byte[] dataByte = org.bouncycastle.util.encoders.Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = org.bouncycastle.util.encoders.Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = org.bouncycastle.util.encoders.Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                result = new String(resultByte, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return result;
    }






    public static void main(String[] args) throws Exception {
        String encryptedData = "5hdq33EvOYYrmlNaL2EPoXprNwdPDy3BTbNtoTl20DdFD1wUjOQCyz+FEy3q6zFjCNwrfRu2tF9krkfOTN5e4KB8Km6fDBanTJ/4yDkyZf445IIkTjrGrkqPbtOy8aaf3qFXiVz6CTefcjahpT5STTkwcX7XWJTAN5FVjndSEYsoPbmquabfrDJYO9cw9RPCmmTkFmpdb44EghImQ4FRYtVYOoZxYmJwZyp96MDlu+gwLNU27RhhwBrt2JQoSFyebz9JziXk8G2t1/+eJH3S/S6fCAUr9tUK+Fsji645n1JCUtk6vf8Y4eNyEO9A0gGEPYNMqncloMTy8SWJCOh411q6UrXG7qyZV+OlanMcq0eyE1/ncDivL0KCzTAoxHAeokOVXThOLrZse1o6AmJLMg==";// 注意encryptedData 需要替换空格为“ + ”
        String sessionKey = "f4cdbc0e-ab79-49f4-8774-0a70eb770f83";
        String iv = "pao+ZEix+XwZ5s1V6cShxQ==";
        System.out.println(decryptNew(encryptedData, sessionKey, iv));

    }
}