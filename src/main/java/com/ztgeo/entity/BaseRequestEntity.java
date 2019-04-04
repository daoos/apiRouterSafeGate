package com.ztgeo.entity;

/**
 * 通用request请求体
 */
public class BaseRequestEntity {

    private String data; // 纯数据
    private String token; // 令牌
    private String sign; // 签名

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
