package com.ztgeo.common;

/**
 * 路由程序统一消息格式
 *
 * @author zoupeidong
 * @version 2018-12-6
 */
public class ApiRouterResponse {

    private int code = 200;
    private String msg;
    private String data;

    public ApiRouterResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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

}
