package com.ztgeo.common;

import com.ztgeo.msg.CodeMsg;
import com.ztgeo.msg.PickUpPlaformMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 中天吉奥业务运行时异常处理类
 *
 * @author zoupeidong
 * @version 2018-12-6
 */
public class ZtgeoBizRuntimeException extends RuntimeException{

    private static Logger log = LoggerFactory.getLogger(ZtgeoBizRuntimeException.class);

    private int code; // 状态码
    private String errorCause = "";  // 异常原因
    public ZtgeoBizRuntimeException() {
    }

    public ZtgeoBizRuntimeException(CodeMsg codeMsg) {
        super(codeMsg.message());
        this.code = codeMsg.statusCode();
    }

    public ZtgeoBizRuntimeException(int code,String message,String errorCause) {
        super(message+(Objects.equals("",errorCause) == true ? "" : "，"+errorCause));
        this.code = code;
        this.errorCause = errorCause;
    }

    public ZtgeoBizRuntimeException(CodeMsg codeMsg, String errorCause) {
        super(codeMsg.message()+(Objects.equals("",errorCause) == true ? "" : "，"+errorCause));
        this.code = codeMsg.statusCode();
        this.errorCause = errorCause;
    }

    public ZtgeoBizRuntimeException(CodeMsg codeMsg, Throwable cause) {
        super(codeMsg.message(), cause);
        this.code = codeMsg.statusCode();
    }

    public ZtgeoBizRuntimeException(Throwable cause) {
        super(cause);
    }

    public ZtgeoBizRuntimeException(CodeMsg codeMsg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(codeMsg.message(), cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorCause() {
        return errorCause;
    }

    public void setErrorCause(String errorCause) {
        this.errorCause = errorCause;
    }
}
