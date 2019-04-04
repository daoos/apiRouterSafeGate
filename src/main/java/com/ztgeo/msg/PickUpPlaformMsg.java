package com.ztgeo.msg;

/**
 * 统一收件平台错误码
 */
public enum PickUpPlaformMsg {

    SUCCESS(200,"流程触发成功"),
    BUSINESS_FAIL(501, "流程触发失败");

    private PickUpPlaformMsg(int statusCode, String message) {
        this.statusCode=statusCode;
        this.message=message;
    }

    public int statusCode() {
        return statusCode;
    }

    public String message() {
        return message;
    }

    private int statusCode; // 状态码
    private String message; // 消息

}
