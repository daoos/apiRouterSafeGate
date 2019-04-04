package com.ztgeo.msg;

public enum CodeMsg {

    SUCCESS(200,"请求成功"),
    /******************sdk异常*******************/

    SDK_INTER_ERROR(300,"工具包内部异常"),
    SDK_SIGN_GENERATE_FAIL(301,"签名失败"),
    SDK_SIGN_VERIFY_FAIL(302,"验签失败"),
    SDK_ENCRYPT_FAIL(303,"加密失败"),
    SDK_DECRYPT_FAIL(304,"解密失败"),
    SDK_PARAM_ERROR(305,"参数异常"),

    /******************共享平台异常*******************/

    TOKEN_EXCEPTION(401, "Token过期或无访问权限"),
    ACCESS_FAIL(402, "访问失败"),
    BLACK_USER(403,"对不起，您的IP已被加入黑名单,如需恢复,请联系平台管理人员"),
    PARAMS_ERROR(404,"参数错误"),
    HEADER_ERROR(405,"缺少必要Header或Header与目标值不对应"),
    SIGN_ERROR(406,"验签失败"),
    FAIL(500, "平台网关内部错误"),
    RECEIVE_EXCEPTION(505,"接收方业务处理错误，待重新发送"),

    /*************统一收件平台流程相关***************/
    PROCESS_SUCCESS(200,"流程触发成功"),
    PROCESS_FAIL(501, "流程触发失败");

    private CodeMsg(int statusCode, String message) {
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
