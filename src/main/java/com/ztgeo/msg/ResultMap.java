package com.ztgeo.msg;

import com.alibaba.fastjson.JSONObject;

/**
 * 统一返回消息格式类
 *
 * @author zoupeidong
 * @see CodeMsg
 */
public class ResultMap<T> {

    private int code;
    private String message;
    private T data;
    private static JSONObject jsonObj = new JSONObject();

    private ResultMap(CodeMsg codeMsg) {
        this.code = codeMsg.statusCode();
        this.message = codeMsg.message();
    }

    private ResultMap(CodeMsg codeMsg, T data) {
        this.code = codeMsg.statusCode();
        this.message = codeMsg.message();
        this.data = data;
    }

    /**
     *  成功，不需传递任何参数
     *     
     */
    @SuppressWarnings("unchecked")
    public static <T> ResultMap<T> ok() {
        return (ResultMap<T>) ok("");
    }

    /**
     *  成功，仅需传递数据
     *     
     */
    public static <T> ResultMap<T> ok(T data) {
        return new ResultMap<T>(CodeMsg.SUCCESS, data);
    }

    /**
     *  成功，仅需传递数据
     *     
     */
    public static <T> ResultMap<T> ok(CodeMsg codeMsg) {
        return new ResultMap<T>(codeMsg);
    }

    /**
     *  异常，不需传递数据
     *     
     */
    @SuppressWarnings("unchecked")
    public static <T> ResultMap<T> error(CodeMsg codeMsg) {
        return (ResultMap<T>) error(codeMsg, "");
    }
    /**
     *  异常，需传递状态和数据
     *     
     */
    public static <T> ResultMap<T> error(CodeMsg codeMsg, T data) {
        return new ResultMap<T>(codeMsg, data);
    }

    @Override
    public String toString() {
        jsonObj.clear();
        jsonObj.put("code", code);
        jsonObj.put("message", message);
        jsonObj.put("data", data);
        return jsonObj.toJSONString();
    }

}
