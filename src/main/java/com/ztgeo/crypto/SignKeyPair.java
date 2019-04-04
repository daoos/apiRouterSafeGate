package com.ztgeo.crypto;

import java.io.Serializable;

/**
 * 签名密钥对
 *
 * @author zoupeidong
 * @version 1.0
 */
public class SignKeyPair implements Serializable {

    private String privateKey; // 私钥
    private String publickKey; // 公钥

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublickKey() {
        return publickKey;
    }

    public void setPublickKey(String publickKey) {
        this.publickKey = publickKey;
    }

    @Override
    public String toString() {
        return "SignKeyPair{" +
                "privateKey='" + privateKey + '\'' +
                ", publickKey='" + publickKey + '\'' +
                '}';
    }

}
