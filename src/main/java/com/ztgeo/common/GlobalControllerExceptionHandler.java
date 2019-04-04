package com.ztgeo.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局controller异常处理
 *
 * @author zoupeidong
 * @version 2018-12-6
 */
@ControllerAdvice("com.ztgeo")
@ResponseBody
public class GlobalControllerExceptionHandler {

    private static final String GLOBAL_EXCEPTION_MSG = "共享平台系统内部错误，请联系平台管理人员";

    /**
     * 通用异常处理，统一返回{code:500,msg:"共享平台系统内部错误，请联系平台负责人"}
     */
    @ExceptionHandler(Exception.class)
    public ApiRouterResponse ExceptionHandler(HttpServletResponse response, Exception ex) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); // 外层status的设置
        return new ApiRouterResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), GLOBAL_EXCEPTION_MSG);
    }

    //返回的HTTP异常状态码为500
    @ExceptionHandler(ZtgeoBizRuntimeException.class)
    public ApiRouterResponse cXBusyException(ZtgeoBizRuntimeException ex) {
        return new ApiRouterResponse(ex.getCode(), ex.getMessage());
    }

}
