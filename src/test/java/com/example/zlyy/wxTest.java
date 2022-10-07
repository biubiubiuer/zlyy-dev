package com.example.zlyy;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import org.apache.commons.codec.binary.Base64;

import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


//import com.sun.org.apache.xml.internal.security.utils.Base64;

public class wxTest {

    public static void main(String[] args) throws Exception
    {
        //这个会报异常
        String sessionKey = "o-kJW4_3AVXz5s96JgUkcBE4M3LA";
        String encryptedData = "JDIIqASgtHXbNkolc7j0cV4f3bakhZMj/Z2gbxJtCCFmzJmbZeUXKujEb4DEAhfrHH90qhVMpKTB+SAAjdicjT7waWtgexWfipIgZS2FREJvMmdNsp/ahhtn68joAhZze458X0YFQ4i01/Y5UFr1XktllN7C+LSb5zVqOzPGG20zER5TtB1XaZig1PmOF4CqO3A7ZV7nUnr0itIDhSXPZhQqJCNwd93EKyi7lVnCBSOF6Fqb3Wp6na3KrKP1lON1UV5g8DDZRJFOpsvVMMP6KNdy8Sf5DTn9PE7+XcZ2yVwiIPBihsA0iPMlv3jhOnzhuEtz7M4+02f0V1MNS31kZuvQb0NnwGGwRb8JrlTF1c70InLMUvgRG66gFE7otx7v/aHvrv02MitbjgL5Lg76O8Og6Tm1/gFkQaPoz4F1AQ+SNTnjgRYSFHwT2AQXusG2hJ678r8+kcUTR1tPRSrc+zBxNoEA3SPNgDZEFE557FI=";
        String iv = "dqNtltSAZGV9Q+O9Hh8mLg==";
        //这个不会报异常
        String sessionKey1 = "tiihtNczf5v6AKRyjwEUhQ==";
        String encryptedData1 ="CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";
        String iv1 = "r7BXXKkLb8qrSNn05n0qiA==";
        System.out.println(decrypt(encryptedData1, sessionKey1, iv1, "UTF-8"));
    }
    public static String decrypt(String data, String key, String iv, String encodingFormat) throws Exception
    {
        //initialize();
        //被加密的数据
        byte[] dataByte = Base64.decodeBase64(data);
        //加密秘钥
        byte[] keyByte = Base64.decodeBase64(key);
        //偏移量
        byte[] ivByte = Base64.decodeBase64(iv);
        try
        {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            //生成iv
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0)
            {
                String result = new String(resultByte, encodingFormat);
                return result;
            }
            return null;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchPaddingException e)
        {
            e.printStackTrace();
        }
        catch (InvalidParameterSpecException e)
        {
            e.printStackTrace();
        }
        catch (InvalidKeyException e)
        {
            e.printStackTrace();
        }
        catch (InvalidAlgorithmParameterException e)
        {
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e)
        {
            e.printStackTrace();
        }
        catch (BadPaddingException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }


}
