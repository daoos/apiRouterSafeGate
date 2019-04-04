package com.ztgeo.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 秘钥的配置类
 * Create by Wei on 2018/9/6
 *
 */
@Component
@ConfigurationProperties(prefix = "secret")
public class SecretConfig {
    private  String WFpriKey;//平台私钥
    private  String WFpubKey;//平台公钥

    public String getWFpriKey() {
        return WFpriKey;
    }

    public void setWFpriKey(String WFpriKey) {
        this.WFpriKey = WFpriKey;
    }

    public String getWFpubKey() {
        return WFpubKey;
    }

    public void setWFpubKey(String WFpubKey) {
        this.WFpubKey = WFpubKey;
    }
}
