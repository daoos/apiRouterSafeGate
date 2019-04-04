package com.ztgeo.crypto;

import java.io.Serializable;

/**
 * 通用响应实体类，机构侧和平台侧均可使用
 *
 * @author zoupeidong
 * @version 2018-12-13
 */
public class CommonResponseEntity implements Serializable{

    // 状态码
    private int code;
    // 消息
    private String msg;
    // 数据
    private String data;
    // 签名
    private String sign;
    // 文件数组,base64
    private String files;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
