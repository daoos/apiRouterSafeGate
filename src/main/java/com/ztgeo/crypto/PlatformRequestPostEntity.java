package com.ztgeo.crypto;

import java.io.Serializable;

/**
 * 平台侧发送post请求数据格式
 *
 * @author zoupeidong
 * @version 2018-12-13
 */
public class PlatformRequestPostEntity implements Serializable{

    // 数据
    private String data;
    // 签名
    private String sign;
    // 文件数组,base64
    private String files;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }
}
