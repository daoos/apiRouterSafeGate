package com.ztgeo.biz;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ztgeo.config.SecretConfig;
import com.ztgeo.mapper.UserKeyInfoMapper;
import com.ztgeo.utils.UserKeyUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ztgeo.entity.UserKeyInfo;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 用户密钥表
 */
@Service
public class UserKeyInfoBiz extends BusinessBiz<UserKeyInfoMapper, UserKeyInfo> {

    @Autowired
    private SecretConfig secretConfig;

    /**
     * 获取数据并调用对称密钥解密，公钥验证签名, 返回所有明文数据(格式:{data:"",token:""})
     *
     * @param bodyData 用户发送的body体数据,格式:{data:"",token:{userID:"",apiID:"",timestamp:""},sign:""}
     * @return 返回验签和解密的数据，格式:{data:"",token:""},其他异常情况均返回null
     */
    public JSONObject decryptAndUnSign(String bodyData) {
        // 获取用户token信息
        JSONObject jsonData = JSONObject.parseObject(bodyData);
        JSONObject tokenJson = jsonData.getJSONObject("token");
        String userId = tokenJson.getString("userID"); // 用户身份标识
        UserKeyInfo userKeyInfo = mapper.selectSymmetricPubkeyByUserIdentityId(userId);
        if (Objects.equals(null, userKeyInfo) || Objects.equals(null, userKeyInfo.getSymmetricPubkey()) || Objects.equals(null, userKeyInfo.getSignPubKey())) { // 未查询到密钥
            return null;
        }
        // 获取密钥解密
        String symmetricPubkey = userKeyInfo.getSymmetricPubkey(); // 对称密钥
        String encryptData = jsonData.getString("data"); // 获取加密的数据
        String decryptData = UserKeyUtils.aesDecrypt(encryptData, symmetricPubkey, StandardCharsets.UTF_8.toString()); // 解密数据
        // 验证签名
        String signPubKey = userKeyInfo.getSignPubKey(); // 签名公钥
        String signStr = jsonData.getString("sign"); // 签名
        boolean verifyResult = UserKeyUtils.signatureVerify(signPubKey, decryptData+tokenJson.toJSONString(), signStr, StandardCharsets.UTF_8.toString());
        if (!verifyResult) return null; // 验证不通过
        // 封装解密后的数据
        JSONObject decryptJson = new JSONObject();
        decryptJson.put("data", decryptData);
        decryptJson.put("token", tokenJson);
        return decryptJson;
    }

    /**
     * 将数据重新加密加签名,返回的数据格式:{data:"",sign:""}
     *
     * @param decryptReqData 解密的请求数据，格式:{data:"",token:""}
     * @return 返回签名和加密的数据, 返回的数据格式:{data:"",sign:""},其他异常情况均返回null,
     */
    public String encryptAndSign(JSONObject decryptReqData) {
        // 获取明文数据
        String data = decryptReqData.getJSONArray("data").toJSONString();
        // 生成签名
        String sign = UserKeyUtils.generateSign(secretConfig.getWFpriKey(), data, "UTF-8");
        // 获取接收方数据加密对称密钥并对数据重新加密
        String apiID = decryptReqData.getJSONObject("token").getString("apiID");
        UserKeyInfo userKeyInfo = mapper.selectKeyInfoByApiId(apiID);
        String symmetricPubKey = userKeyInfo.getSymmetricPubkey(); // 用于数据加密的对称密钥
        String encryptData = UserKeyUtils.aesEncrypt(data, symmetricPubKey, StandardCharsets.UTF_8.toString());
        // 封装结果，返回数据
        JSONObject resultJson = new JSONObject();
        resultJson.put("data", encryptData);
        resultJson.put("sign", sign);
        return resultJson.toJSONString();
    }

    /**
     * 根据APIpubkey查询该API对应的机构的密钥信息
     *
     * @param apiPubkey 公开的API标识
     * @return 该API拥有的机构的密钥信息
     */
    public UserKeyInfo selectKeyInfoByApiId(String apiPubkey) {
        return mapper.selectKeyInfoByApiId(apiPubkey);
    }

    /**
     * 通过用户身份标识查询密钥信息
     *
     * @param userIdentityId 用户身份标识
     * @return 用户密钥信息
     */
    public UserKeyInfo selectSymmetricPubkeyByUserIdentityId(String userIdentityId){
        return mapper.selectSymmetricPubkeyByUserIdentityId(userIdentityId);
    }

    /**
     * 查询用户的登录名
     *
     * @param userPubkey 用户身份标识
     * @return 用户登录名
     */
    public String selectUsernameByUserPubKey(String userPubkey) {
        return mapper.selectUsernameByUserPubKey(userPubkey);
    }
}