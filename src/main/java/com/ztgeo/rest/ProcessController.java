package com.ztgeo.rest;

import com.ztgeo.biz.WfmProcessBiz;
import com.ztgeo.common.ZtgeoBizRuntimeException;
import com.ztgeo.msg.CodeMsg;
import com.ztgeo.msg.ResultMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模板流程controller
 */
@RequestMapping("/process")
@RestController
public class ProcessController {

    private Logger log = LoggerFactory.getLogger(ProcessController.class);
    @Autowired
    private WfmProcessBiz wfmProcessBiz;

    /**
     * 流程触发接口(与统一收件平台交互)
     *
     * @param businessTypeName 业务类型
     * @param businessNo 业务编号/受理编号
     */
    @GetMapping("triggerProcess")
    public String triggerSimpleProcess(@RequestParam("businessTypeName") String businessTypeName,@RequestParam("businessNo") String businessNo){
        log.info("统一收件平台收件完成，触发共享平台流程");
        // 验证参数非空
        if(StringUtils.isBlank(businessTypeName) || StringUtils.isBlank(businessNo)){
            log.error("未获取到业务类型名称或业务编号");
            // 未获取到业务类型名称或业务编号
            throw new ZtgeoBizRuntimeException(CodeMsg.PROCESS_FAIL,"未获取到业务类型名称或业务编号");
        }
        log.info("校验参数完成，开始创建流程");
        // 触发流程
        wfmProcessBiz.createSimpleActivity(businessTypeName,businessNo);
        log.info("流程创建成功");
        return ResultMap.ok(CodeMsg.PROCESS_SUCCESS).toString();
    }

}
