package com.example.zlyy.util;

import org.apache.commons.codec.binary.Base64;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

/**
 * 封装对外访问方法
 * @author liuyazhuang
 *
 */
public class WXCore {

    private static final String WATERMARK = "watermark";
    private static final String APPID = "appid";
    
    
    /**
     * 解密数据
     * @return
     * @throws Exception
     */
    public static String decrypt(String appId, String encryptedData, String sessionKey, String iv){
        String result = "";
        try {
            AES aes = new AES();
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
            if(null != resultByte && resultByte.length > 0){
                result = new String(WxPKCS7Encoder.decode(resultByte));
                JSONObject jsonObject = JSONObject.fromObject(result);
                String decryptAppid = jsonObject.getJSONObject(WATERMARK).getString(APPID);
                if(!appId.equals(decryptAppid)){
                    result = "";
                }
            }
        } catch (Exception e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws Exception{
        String appId = "wx4f4bc4dec97d474b";
        String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";
        String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
        String iv = "r7BXXKkLb8qrSNn05n0qiA==";
        
        String encryptedData1 = "5hdq33EvOYYrmlNaL2EPoXprNwdPDy3BTbNtoTl20DdFD1wUjOQCyz+FEy3q6zFjCNwrfRu2tF9krkfOTN5e4KB8Km6fDBanTJ/4yDkyZf445IIkTjrGrkqPbtOy8aaf3qFXiVz6CTefcjahpT5STTkwcX7XWJTAN5FVjndSEYsoPbmquabfrDJYO9cw9RPCmmTkFmpdb44EghImQ4FRYtVYOoZxYmJwZyp96MDlu+gwLNU27RhhwBrt2JQoSFyebz9JziXk8G2t1/+eJH3S/S6fCAUr9tUK+Fsji645n1JCUtk6vf8Y4eNyEO9A0gGEPYNMqncloMTy8SWJCOh411q6UrXG7qyZV+OlanMcq0eyE1/ncDivL0KCzTAoxHAeokOVXThOLrZse1o6AmJLMg==";
        String sessionKey1 = "f4cdbc0e-ab79-49f4-8774-0a70eb770f83";
        String iv1 = "pao+ZEix+XwZ5s1V6cShxQ==";
        
        System.out.println(decrypt(appId, encryptedData1, sessionKey1, iv1));
    }
}
