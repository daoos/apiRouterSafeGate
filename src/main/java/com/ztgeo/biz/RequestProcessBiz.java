package com.ztgeo.biz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ztgeo.config.SecretConfig;
import com.ztgeo.entity.ApiBaseInfo;
import com.ztgeo.entity.ApiHeaderInfo;
import com.ztgeo.entity.UserKeyInfo;
import com.ztgeo.utils.UserKeyUtils;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * 处理用户request请求，封装并转发用户请求
 */
@Service
public class RequestProcessBiz {

    @Autowired
    private ApiBaseInfoBiz apiBaseInfoBiz;
    @Autowired
    private UserKeyInfoBiz userKeyInfoBiz;
    @Autowired
    private OkHttpClient okHttpClient;
    @Autowired
    private SecretConfig secretConfig;

    /**
     * 转发contentType为application/json的数据
     *
     * @param token      token格式,{userID:"",apiID:"",timestamp:""}
     * @param secretData 加密的body数据
     * @return API没有启用或其他异常，返回空字符串，否则返回responseBody
     */
    public String dispatchApplicationJsonData(HttpServletRequest req, JSONObject token, String secretData) {
        try {
            String apiID = token.getString("apiID");
            String userID = token.getString("userID");
            // 查询apiId的转发的目标url
            ApiBaseInfo apiBaseInfo = apiBaseInfoBiz.selectByApiPubkey(apiID);
            if (Objects.equals(null, apiBaseInfo)) {
                return "";
            }
            // 获取发送url
            String url = apiBaseInfo.getBaseUrl() + apiBaseInfo.getPath();
            Request.Builder builder = new Request.Builder().url(url);
            // Header赋值
            List<ApiHeaderInfo> headerInfoList = apiBaseInfo.getApiHeaderInfoList();
            for (int i = 0; i < headerInfoList.size(); i++) {
                String headerName = headerInfoList.get(i).getHeaderName();
                if (!Objects.equals(null, req.getHeader(headerName))) { // 若header存在，则获取并赋值
                    builder.header(headerName, req.getHeader(headerName));
                }
            }
            // Body赋值
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json,charset=utf-8"), secretData);
            // 构造request，发送请求
            Request request = builder.post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            String responseData = response.body().string();
            // 对返回的数据解密验证签名，数据格式{data:"",sign:""}，用平台私钥加签名，用发送方的公钥加密数据,数据格式{data:"",sign:""}
            UserKeyInfo userKeyInfo = userKeyInfoBiz.selectKeyInfoByApiId(apiID);
            JSONObject rspJSON = JSONObject.parseObject(responseData);
            String rspData = rspJSON.getString("data");
            String rspSign = rspJSON.getString("sign");
            String rspDecryptData = UserKeyUtils.aesDecrypt(rspData, userKeyInfo.getSymmetricPubkey(), StandardCharsets.UTF_8.toString()); // 解密的数据
            boolean signVerifyResult = UserKeyUtils.signatureVerify(userKeyInfo.getSignPubKey(), rspDecryptData, rspSign, StandardCharsets.UTF_8.toString()); // 验证签名
            if (!signVerifyResult) return ""; // 验证不通过
            String rspNewSign = UserKeyUtils.generateSign(secretConfig.getWFpriKey(), rspDecryptData, "UTF-8"); // 使用平台私钥对数据加签名
            // 查询发送方数据加密密钥
            UserKeyInfo sendUserKeyInfo = userKeyInfoBiz.selectSymmetricPubkeyByUserIdentityId(userID);
            String symmetricKey = sendUserKeyInfo.getSymmetricPubkey();
            // 数据加密
            String rspEncryptData = UserKeyUtils.aesEncrypt(rspDecryptData, symmetricKey, StandardCharsets.UTF_8.toString());
            // 封装加密加签的响应数据
            JSONObject rspEncryptJson = new JSONObject();
            rspEncryptJson.put("data", rspEncryptData);
            rspEncryptJson.put("sign", rspNewSign);
            return rspEncryptJson.toJSONString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
