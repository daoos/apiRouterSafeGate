package com.ztgeo.rest;

import com.alibaba.fastjson.JSONObject;
import com.ztgeo.biz.*;
import com.ztgeo.common.ZtgeoBizRuntimeException;
import com.ztgeo.config.SecretConfig;
import com.ztgeo.crypto.*;
import com.ztgeo.entity.ApiBaseInfo;
import com.ztgeo.entity.HttpDataType;
import com.ztgeo.entity.UserKeyInfo;
import com.ztgeo.msg.CodeMsg;
import com.ztgeo.msg.ResultMap;
import com.ztgeo.utils.StringUtils;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
public class RouterController {
    private Logger log = LoggerFactory.getLogger(RouterController.class);
    @Autowired
    private ApiBaseInfoBiz apiBaseInfoBiz;
    @Autowired
    private UserKeyInfoBiz userKeyInfoBiz;
    @Autowired
    private RequestJsonProcessBiz requestJsonProcessBiz;
    @Autowired
    private WfmActivityBiz wfmActivityBiz;
    @Autowired
    private SecretConfig secretConfig;
    @Autowired
    private ApiAccessRecordBiz apiAccessRecordBiz;
    @Autowired
    private NoticeUserRelBiz noticeUserRelBiz;

    /**
     * 转发get请求,请求形式http://xxx.xxx.xxx.xxx:xxxx?data=xxxxx&token=xxxx
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String routerAccess(HttpServletRequest request) {
        try {
            // 获取data和token数据
            String reqData = request.getParameter("data");
            String reqToken = URLDecoder.decode(request.getParameter("token"), StandardCharsets.UTF_8.toString());
            // 转换为预定义响应实体类
            TokenEntity tokenEntity = JSONObject.parseObject(reqToken, TokenEntity.class);
            String apiID = tokenEntity.getApiID();
            String userID = tokenEntity.getUserID();
            // 查询userID对应的密钥信息
            UserKeyInfo userKeyInfo = userKeyInfoBiz.selectSymmetricPubkeyByUserIdentityId(userID);
            String aesKeyFromCorp = userKeyInfo.getSymmetricPubkey();
            if (StringUtils.isBlank(aesKeyFromCorp))
                throw new ZtgeoBizRuntimeException(CodeMsg.FAIL,"未查询到aes密钥");
            reqData = URLDecoder.decode(reqData, StandardCharsets.UTF_8.toString());
            String decryptData = CryptographyOperation.aesDecrypt( aesKeyFromCorp,reqData);
            // 查询目标API对应的密钥信息
            UserKeyInfo userKeyInfoEncrypt = userKeyInfoBiz.selectKeyInfoByApiId(apiID);
            String aesKeyToCorp = userKeyInfoEncrypt.getSymmetricPubkey();
            if (StringUtils.isBlank(aesKeyToCorp))
                throw new ZtgeoBizRuntimeException(CodeMsg.FAIL,"未查询到aes密钥");
            String encryptData = CryptographyOperation.aesEncrypt(aesKeyToCorp,decryptData);
            encryptData = URLEncoder.encode(encryptData,StandardCharsets.UTF_8.toString());
            // 查询apiId的转发的目标url
            ApiBaseInfo apiBaseInfo = apiBaseInfoBiz.selectByApiPubkey(apiID);
            // 存储发送的数据
            String primaryKey = apiAccessRecordBiz.insertApiRecord(request,apiBaseInfo,encryptData);
            String url = apiBaseInfo.getBaseUrl() + apiBaseInfo.getPath() + "?data=" + encryptData;
            String rspData = HttpOperation.sendGetHttp(url);
            // 存储收到的数据
            if(StringUtils.isBlank(rspData)){
                throw new ZtgeoBizRuntimeException(CodeMsg.FAIL,"未接收到响应数据");
            }else{
                apiAccessRecordBiz.updateApiRecord(primaryKey,rspData);
            }
            // 转换为预定义响应实体类
            CommonResponseEntity commonResponseEntity = JSONObject.parseObject(rspData,CommonResponseEntity.class);
            String data = commonResponseEntity.getData();
            String sign = commonResponseEntity.getSign();
            // 验签
            boolean verifyResult = CryptographyOperation.signatureVerify(userKeyInfoEncrypt.getSignPubKey(),data,sign);
            if (Objects.equals(verifyResult,false))
                throw new ZtgeoBizRuntimeException(CodeMsg.SIGN_ERROR);
            // 解密
            String decryptRspData = CryptographyOperation.aesDecrypt(aesKeyToCorp,data);
            // 加密
            String encryptRspData = CryptographyOperation.aesEncrypt(aesKeyFromCorp,decryptRspData);
            // 加签
            String encryptRspSign = CryptographyOperation.generateSign(secretConfig.getWFpriKey(),encryptRspData);
            commonResponseEntity.setData(encryptRspData);
            commonResponseEntity.setSign(encryptRspSign);
            return JSONObject.toJSONString(commonResponseEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ZtgeoBizRuntimeException(CodeMsg.FAIL);
        }
    }

    /**
     * 转发post请求,数据格式：{messageTo:"",dataType:"",token:"",data:"",sign:""}
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String routerAccess(HttpServletRequest request, @RequestBody String bodyStr) {
        ClientRequestPostEntity clientRequestPostEntity = JSONObject.parseObject(bodyStr, ClientRequestPostEntity.class);
        String messageTo = clientRequestPostEntity.getMessageTo();
        if (Objects.equals(messageTo, CryptographyConstants.MESSAGE_TO_CORP)){ // 机构发送给机构
            return requestJsonProcessBiz.processPostData(clientRequestPostEntity,request);
        } else if(Objects.equals(messageTo, CryptographyConstants.MESSAGE_TO_NoticeCORP)){
            return requestJsonProcessBiz.processNoticePostData(clientRequestPostEntity,request);
        } else if (Objects.equals(messageTo, CryptographyConstants.MESSAGE_TO_PLATFORM)){ // 机构发送给平台(通知)
            wfmActivityBiz.updateActivityStepStatus(clientRequestPostEntity);
            return CodeMsg.PROCESS_SUCCESS.toString();
        } else{
            throw new ZtgeoBizRuntimeException(CodeMsg.PARAMS_ERROR,"未获取到messageTo参数");
        }
    }
    /**
     * 转发post请求,数据格式：{httpDataType:"json",messageType"ztgeo_notice",noticeCode:"ztgeo_notice_1",token:"",data:""}
     */
    @RequestMapping(value = "/routerNoticeAccess", method = RequestMethod.POST)
    public String routerNoticeAccess(HttpServletRequest request, @RequestBody String bodyStr) {
        JSONObject reqJson = JSONObject.parseObject(bodyStr);
        String httpDataType = reqJson.getString("httpDataType");
        if (HttpDataType.JSON_TYPE.equalsIgnoreCase(httpDataType)) {
           return requestJsonProcessBiz.processJSON(reqJson,request);
        } else if (HttpDataType.XML_TYPE.equalsIgnoreCase(httpDataType)) {
            // 处理xml数据
            System.out.println("暂时只处理Json格式的,为后期开发需要");
        }
        return ResultMap.ok("").toString();
    }
    /**
     * 通知接口，用于群发HTTP, 固定格式:{data:{},userID:"",typeID:""}
     */
    @PostMapping("/noticeSend")
    public String noticeSend(@RequestBody String bodyStr) {
      //noticeUserRelBiz.sendNotice(bodyStr);
        return ResultMap.ok().toString();
    }

}
