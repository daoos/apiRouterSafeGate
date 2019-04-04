package com.ztgeo.crypto;

import java.io.Serializable;

/**
 * 机构侧请求机构或平台数据格式
 *
 * @author zoupeidong
 * @version 2018-12-13
 */
public class ClientRequestPostEntity implements Serializable{

    // 消息的接收方：平台(platform)或者机构(corp)
    private String messageTo;
    // data的格式json/xml
    private String dataType;
    // token
    private TokenEntity token;
    // data
    private String data;
    // sign
    private String sign;
    // 小文件,base64数组
    private String files;

    public String getMessageTo() {
        return messageTo;
    }

    public void setMessageTo(String messageTo) {
        this.messageTo = messageTo;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public TokenEntity getToken() {
        return token;
    }

    public void setToken(TokenEntity token) {
        this.token = token;
    }

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
