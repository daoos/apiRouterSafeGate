package com.ztgeo.biz;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.ztgeo.common.ZtgeoBizRuntimeException;
import com.ztgeo.config.SecretConfig;
import com.ztgeo.crypto.*;
import com.ztgeo.entity.*;
import com.ztgeo.msg.CodeMsg;
import com.ztgeo.msg.ResultMap;
import com.ztgeo.utils.HttpUtils;
import com.ztgeo.utils.UserKeyUtils;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class RequestJsonProcessBiz {

    @Autowired
    private UserKeyInfoBiz userKeyInfoBiz;
    @Autowired
    private ApiAccessRecordBiz apiAccessRecordBiz;
    @Autowired
    private ApiBaseInfoBiz apiBaseInfoBiz;
    @Autowired
    private OkHttpClient okHttpClient;
    @Autowired
    private SecretConfig secretConfig;
    @Autowired
    private NoticeUserRelBiz noticeUserRelBiz;
    @Autowired
    private MongoClient mongoClient;
    @Value("${customAttributes.dbName}")
    private String dbName; // 存储用户发送数据的数据库名

    /**
     * 处理post数据
     * content-type:application/json
     */
    public String processPostData(ClientRequestPostEntity clientRequestPostEntity, HttpServletRequest request) {
        // 获取token数据
        TokenEntity tokenEntity = clientRequestPostEntity.getToken();
        String apiID = tokenEntity.getApiID();
        String userID = tokenEntity.getUserID();
        // 获取数据和签名
        String data = clientRequestPostEntity.getData();
        String sign = clientRequestPostEntity.getSign();
        if (StringUtils.isBlank(data) || StringUtils.isBlank(sign))
            throw new ZtgeoBizRuntimeException(CodeMsg.PARAMS_ERROR, "未获取到数据或签名");
        // 获取密钥信息
        UserKeyInfo userKeyInfo = userKeyInfoBiz.selectSymmetricPubkeyByUserIdentityId(userID); // 获取密钥信息
        String signPubKey = userKeyInfo.getSignPubKey();
        // 解密数据
        String aesKey = userKeyInfo.getSymmetricPubkey();
        // 验证签名
        String reqDecryptData = CryptographyOperation.aesDecrypt(aesKey, data);
        boolean verifyResult = CryptographyOperation.signatureVerify(signPubKey, reqDecryptData, sign);
        if (Objects.equals(verifyResult, false))
            throw new ZtgeoBizRuntimeException(CodeMsg.SIGN_ERROR);

        // 获取接收方机构的密钥
        UserKeyInfo userKeyInfoReceive = userKeyInfoBiz.selectKeyInfoByApiId(apiID);
        String receiveSignPubKey = userKeyInfoReceive.getSignPubKey();
        String receiveAesKey = userKeyInfoReceive.getSymmetricPubkey();
        if (StringUtils.isBlank(receiveSignPubKey) || StringUtils.isBlank(receiveAesKey))
            throw new ZtgeoBizRuntimeException(CodeMsg.FAIL, "未查询到接收方密钥信息");
        // 重新加密加签
        String receiveEncryptData = CryptographyOperation.aesEncrypt(receiveAesKey, reqDecryptData);
        String receiveSign = CryptographyOperation.generateSign(secretConfig.getWFpriKey(), receiveEncryptData);
        // 查询转发地址
        ApiBaseInfo apiBaseInfo = apiBaseInfoBiz.selectByApiPubkey(apiID);
        String url = apiBaseInfo.getBaseUrl() + apiBaseInfo.getPath();
        // 封装数据
        PlatformRequestPostEntity platformRequestPostEntity = new PlatformRequestPostEntity();
        platformRequestPostEntity.setData(receiveEncryptData);
        platformRequestPostEntity.setSign(receiveSign);
        platformRequestPostEntity.setFiles(clientRequestPostEntity.getFiles());
        // 存储发送的数据
        String primaryKey = apiAccessRecordBiz.insertApiRecord(request, apiBaseInfo, JSONObject.toJSONString(platformRequestPostEntity));
        // 发送并接收数据
        String rspData = HttpOperation.sendJsonHttp(url, JSONObject.toJSONString(platformRequestPostEntity));
        // 存储收到的数据
        if (com.ztgeo.utils.StringUtils.isBlank(rspData)) {
            throw new ZtgeoBizRuntimeException(CodeMsg.FAIL, "未接收到响应数据");
        } else {
            apiAccessRecordBiz.updateApiRecord(primaryKey, rspData);
        }
        // 转换为预定义响应实体类
        CommonResponseEntity commonResponseEntity = JSONObject.parseObject(rspData, CommonResponseEntity.class);
        String rspEncryptData = commonResponseEntity.getData();
        String rspSignData = commonResponseEntity.getSign();
        // 验证签名
        boolean rspVerifyResult = CryptographyOperation.signatureVerify(receiveSignPubKey, rspEncryptData, rspSignData);
        if (Objects.equals(rspVerifyResult, false))
            throw new ZtgeoBizRuntimeException(CodeMsg.SIGN_ERROR);
        // 解密
        String rspDecryptData = CryptographyOperation.aesDecrypt(receiveAesKey, rspEncryptData);
        // 重新加密加签
        rspEncryptData = CryptographyOperation.aesEncrypt(aesKey, rspDecryptData);
        rspSignData = CryptographyOperation.generateSign(secretConfig.getWFpriKey(), rspEncryptData);
        commonResponseEntity.setData(rspEncryptData);
        commonResponseEntity.setSign(rspSignData);
        return JSONObject.toJSONString(commonResponseEntity);
    }

    /**
     * 处理Json数据
     */
    public String processJSON(JSONObject reqJson, HttpServletRequest request) {
        // 判断消息类型(数据、通知、业务流程相关)
        String messageType = reqJson.getString("messageType");
        //处理通知
        if (MessageType.ZTGEO_NOTICE.equalsIgnoreCase(messageType)) { // 通知
            return  noticeUserRelBiz.sendNotice(reqJson, request);
        } else {
            throw new ZtgeoBizRuntimeException(CodeMsg.PARAMS_ERROR, "未获取到可用messageType参数");
        }
    }

//    /**
//     * 处理通知信息
//     */
//    public String processNotice(JSONObject reqJson, HttpServletRequest request) {
//        noticeUserRelBiz.sendNotice(reqJson, request);
//        return "发送成功";
//    }

    /**
     * 处理数据
     *
     * @param reqJson 请求的json数据{httpDataType:"",messageType"",token:"",sign:"",data:"",files:""}
     * @deprecated
     */
    public String processData(JSONObject reqJson, HttpServletRequest requestReceive) {
        try {
            // 验证数据和签名，返回解密后的数据
            JSONObject tokenEntityJson = reqJson.getJSONObject("token");
            String apiID = tokenEntityJson.getString("apiID");
            String userID = tokenEntityJson.getString("userID");
            UserKeyInfo userKeyInfo = userKeyInfoBiz.selectSymmetricPubkeyByUserIdentityId(userID); // 获取密钥信息
            String signPubKey = userKeyInfo.getSignPubKey();
            String encryptData = reqJson.getString("data");
            String sign = reqJson.getString("sign");
            // 获取解密数据
            String decryptData = UserKeyUtils.aesDecrypt(encryptData, userKeyInfo.getSymmetricPubkey(), StandardCharsets.UTF_8.toString());
            boolean signVerify = UserKeyUtils.signatureVerify(signPubKey, decryptData, sign, StandardCharsets.UTF_8.toString());
            if (!signVerify) // 签名验证不通过
                return "";
            // 将数据重新加密加签名
            UserKeyInfo userKeyInfoEncrypt = userKeyInfoBiz.selectKeyInfoByApiId(apiID);
            String sendSign = UserKeyUtils.generateSign(secretConfig.getWFpriKey(), decryptData, StandardCharsets.UTF_8.toString());
            String sendData = UserKeyUtils.aesEncrypt(decryptData, userKeyInfoEncrypt.getSymmetricPubkey(), StandardCharsets.UTF_8.toString());
            reqJson.remove("data");
            reqJson.remove("sign");
            reqJson.put("data", sendData);
            reqJson.put("sign", sendSign);
            // 查询apiId的转发的目标url
            ApiBaseInfo apiBaseInfo = apiBaseInfoBiz.selectByApiPubkey(apiID);
            if (Objects.equals(null, apiBaseInfo)) {
                return "";
            }
            // 获取发送url
            String url = apiBaseInfo.getBaseUrl() + apiBaseInfo.getPath();
            Request.Builder builder = new Request.Builder().url(url);
            // Body赋值
            reqJson.remove("token");
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json,charset=utf-8"), reqJson.toJSONString());
            // 构造request，发送请求
            Request request = builder.post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            String responseData = response.isSuccessful() == true ? response.body().string() : "";
            // 存储至mongoDB
            String userLoginName = userKeyInfoBiz.selectUsernameByUserPubKey(userID);// 查询用户的登录名
            CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
                    CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
            MongoDatabase mongoDB = mongoClient.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);
            MongoCollection<HttpEntity> storeCollection = mongoDB.getCollection(userLoginName, HttpEntity.class);
            HttpEntity httpEntity = new HttpEntity();
            httpEntity.setSendUserID(userKeyInfo.getUserRealId() == null ? "" : userKeyInfo.getUserRealId()); // 发送者真实ID
            httpEntity.setApiID(apiID); // 访问的接口公开ID
            httpEntity.setApiName(userKeyInfoEncrypt.getApiName()); // 访问的接口名称
            httpEntity.setReceiveUserID(userKeyInfoEncrypt.getUserRealId()); // 接收者真实ID
            httpEntity.setReceiverUserName(userKeyInfoEncrypt.getName()); // 接收者机构名称
            httpEntity.setApiPath(userKeyInfoEncrypt.getBaseUrl() + userKeyInfoEncrypt.getPath());
            httpEntity.setSourceUrl(HttpUtils.getIpAdrress(requestReceive));
            httpEntity.setMethod(requestReceive.getMethod());
            httpEntity.setContentType(requestReceive.getContentType());
            LocalDateTime localTime = LocalDateTime.now();
            httpEntity.setYear(localTime.getYear());
            httpEntity.setMonth(localTime.getMonthValue());
            httpEntity.setDay(localTime.getDayOfMonth());
            httpEntity.setHour(localTime.getHour());
            httpEntity.setMinute(localTime.getMinute());
            httpEntity.setSecond(localTime.getSecond());
            httpEntity.setCurrentTime(Instant.now().getEpochSecond());
            httpEntity.setSendBody(decryptData); // 发送的数
//            String responseData = response.isSuccessful() == true ? response.body().string() :""; // 预置返回的数据,发送成功则存储成功返回的数据，发送失败则存储空字符串

            // 解密验签
            JSONObject rspJson = JSONObject.parseObject(responseData);
            String rspDecryptData = UserKeyUtils.aesDecrypt(rspJson.getString("data"), userKeyInfoEncrypt.getSymmetricPubkey(), StandardCharsets.UTF_8.toString());
            boolean rspSignVerify = UserKeyUtils.signatureVerify(userKeyInfoEncrypt.getSignPubKey(), rspDecryptData, rspJson.getString("sign"), StandardCharsets.UTF_8.toString());
            if (!rspSignVerify) { // 签名验证不通过
                httpEntity.setReceiveBody(rspDecryptData); // 接收的数据
                storeCollection.insertOne(httpEntity);
                return "";
            }
            httpEntity.setReceiveBody(rspDecryptData); // 接收的数据
            storeCollection.insertOne(httpEntity);
            // 加密加签(平台密钥)
            String rspSendSign = UserKeyUtils.generateSign(secretConfig.getWFpriKey(), rspDecryptData, StandardCharsets.UTF_8.toString());
            String rspSendData = UserKeyUtils.aesEncrypt(rspDecryptData, userKeyInfo.getSymmetricPubkey(), StandardCharsets.UTF_8.toString());
            rspJson.remove("sign");
            rspJson.remove("data");
            rspJson.put("sign", rspSendSign);
            rspJson.put("data", rspSendData);
            return rspJson.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}
