package com.ztgeo.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;
public class UserKeyUtils {

    private static final String ALGORITHM="AES";   //算法说明
    private static final String AES_CBC_PCK_ALG = "AES/CBC/PKCS5Padding";
    private static String key = UUID.randomUUID().toString().replaceAll("-","");  //自定义秘钥
    private static final String MD5WITHRSA =  "MD5withRSA";//验签加签的方法


    /**
     *@描述  生成aes加密加密秘钥的key 这里使用uuid进行生成
     *@参数  []
     *@返回值  返回生成的aeskey
     *@创建人  Wei
     *@创建时间  2018/9/5
     */
    public static String generateAesKey(){
        String resultKey = "";
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            //初始化密钥
            keyGenerator.init(new SecureRandom(key.getBytes()));
            //生成秘钥
            SecretKey getKey = keyGenerator.generateKey();
            resultKey=new String(Base64.getEncoder().encode(getKey.getEncoded()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("生成aes公钥异常");
        }
        return resultKey;
    }


    /**
     *@描述
     *@参数  [privateKeyStr加签的私钥 data需要加签的数据（按照既定规则排序）, charset（字符集）]
     *@返回值  返回加签后的结果（已进行了base64）
     *@创建人  Wei
     *@创建时间  2018/9/10
     */
    public static String generateSign(String privateKeyStr,String data,String charset){
        try {
            Signature signature = Signature.getInstance(MD5WITHRSA);
            PrivateKey privateKey = getPrivateKeyFromStr(privateKeyStr);//使用私钥字符串形成私钥
            signature.initSign(privateKey);
            signature.update(data.getBytes(charset));
            return new String(Base64.getEncoder().encode(signature.sign()));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("加签失败");
        }
    }

    /**
     *@描述  从字符串中构建私钥
     *@参数  [privateKeyStr]
     *@返回值  私钥
     *@创建人  Wei
     *@创建时间  2018/9/10
     */
    private static PrivateKey getPrivateKeyFromStr(String privateKeyStr){
         try {
             byte[] decodeBytes = Base64.getDecoder().decode(privateKeyStr);//对base64位的数据进行解码
             //重新构造私钥 默认使用PKCS8 长度1024 对于非java语言需要使用PKCS1
             PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodeBytes);
             KeyFactory keyFactory = KeyFactory.getInstance("RSA");
             return keyFactory.generatePrivate(keySpec);
         }catch (Exception e){
             e.printStackTrace();
             throw new RuntimeException("字符串生成私钥异常");
         }

    }

    /**
     *@描述  MD5withRSA验签 通过库中查询的客户端key字符串进行验签
     *@参数  [publicKeyStr 公钥字符串（库中抓取）, data（加签的明文数据，调用aes解密后获取）, signStr（加签的串）, charset（字符集）]
     *@返回值  boolean 验签通过为true 验签失败为false
     *@创建人  Wei
     *@创建时间  2018/9/10
     */
     public static boolean signatureVerify(String publicKeyStr,String data,String signStr,String charset){
         //初始化变量默认值 false
         boolean verify = false;
         //从字符串转换成成公钥
         try {
             byte[] decode = Base64.getDecoder().decode(signStr); //签名反编译
             PublicKey publicKey = getPublicKeyFromStr(publicKeyStr);
             Signature signature = Signature.getInstance(MD5WITHRSA);
             signature.initVerify(publicKey); //填充公钥
             signature.update(data.getBytes(charset)); //字符集
             verify = signature.verify(decode);
         } catch (Exception e) {
             e.printStackTrace();
             throw new RuntimeException("验签过程中异常!");
         }
         return verify;
     }




    /**
     *@描述  从字符串生成公钥
     *@参数  [publicKeyStr]
     *@返回值  公钥
     *@创建人  Wei
     *@创建时间  2018/9/10
     */
    private static PublicKey getPublicKeyFromStr(String publicKeyStr){
        //对base64位的数据进行解码
        try {
            byte[] decodeBytes = Base64.getDecoder().decode(publicKeyStr);
            //重新构造公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodeBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("生成公钥异常");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new RuntimeException("生成公钥异常");
        }

    }


    /**
     * AES加密
     *s
     * @param content
     * @param aesKey
     * @param charset
     * @return
     *
     */
    public static String aesEncrypt(String content, String aesKey, String charset){
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);

            IvParameterSpec iv = new IvParameterSpec(initIv(AES_CBC_PCK_ALG));
            cipher.init(Cipher.ENCRYPT_MODE,
                    new SecretKeySpec(Base64.getDecoder().decode(aesKey.getBytes()), "AES"), iv);

            byte[] encryptBytes = cipher.doFinal(content.getBytes(charset));
            return new String(Base64.getEncoder().encode(encryptBytes));
        } catch (Exception e) {
            throw new RuntimeException("aes加密失败");
        }
    }

    /**
     * AES解密
     *
     * @param content
     * @param key
     * @param charset
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String content, String key, String charset) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
            IvParameterSpec iv = new IvParameterSpec(initIv(AES_CBC_PCK_ALG));
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.getDecoder().decode(key.getBytes()),
                    "AES"), iv);

            byte[] cleanBytes = cipher.doFinal(Base64.getDecoder().decode(content.getBytes()));
            return new String(cleanBytes, charset);
        } catch (Exception e) {
            throw  new RuntimeException("解密失败");
        }
    }

    /**
     * 初始向量的方法, 全部为0. 这里的写法适合于其它算法,针对AES算法的话,IV值一定是128位的(16字节).
     *
     * @param fullAlg
     * @return
     * @throws GeneralSecurityException
     */
    private static byte[] initIv(String fullAlg) {
        try {
            Cipher cipher = Cipher.getInstance(fullAlg);
            int blockSize = cipher.getBlockSize();
            byte[] iv = new byte[blockSize];
            for (int i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }
            return iv;
        } catch (Exception e) {

            int blockSize = 16;
            byte[] iv = new byte[blockSize];
            for (int i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }
            return iv;
        }
    }

}
