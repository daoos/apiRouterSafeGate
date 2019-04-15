package com.ztgeo.crypto;

/**
 * 常用的密码算法参数常量
 *
 * @author zoupeidong
 * @version 2018-12-11
 */
public class CryptographyConstants {

    /******************对称加密解密算法相关*******************/
    public static final String AES = "AES";
    public static final String AES_CBC_PCK_ALG = "AES/CBC/PKCS5Padding";

    /***********************签名算法相关**********************/
    public static final String RSA = "RSA";
    public static final String MD5_WIEH_RSA = "MD5withRSA";
    public static final String SHA1_WIEH_RSA = "SHA1withRSA";

    /*****************消息发送对象messageTo相关***************/
    public static final String MESSAGE_TO_CORP = "corp";
    public static final String MESSAGE_TO_PLATFORM = "platform";
    public static final String MESSAGE_TO_NoticeCORP = "noticecorp";

    /*******************数据类型dataType相关******************/
    public static final String DATA_TYPE_JSON = "json";
    public static final String DATA_TYPE_XML = "xml";


}
