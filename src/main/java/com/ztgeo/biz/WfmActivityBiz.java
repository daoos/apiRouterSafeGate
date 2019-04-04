package com.ztgeo.biz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ztgeo.common.ZtgeoBizRuntimeException;
import com.ztgeo.crypto.ClientRequestPostEntity;
import com.ztgeo.crypto.CryptographyOperation;
import com.ztgeo.entity.UserKeyInfo;
import com.ztgeo.entity.WfmActivity;
import com.ztgeo.mapper.WfmActivityMapper;
import com.ztgeo.msg.CodeMsg;
import com.ztgeo.utils.ProcessUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 模板活动表
 *
 * @author zoupeidong
 * @version 2018-11-26 15:26:20
 * @email 806316372@qq.com
 */
@Service
public class WfmActivityBiz extends BusinessBiz<WfmActivityMapper, WfmActivity> {


    @Autowired
    private  UserKeyInfoBiz userKeyInfoBiz;
    /**
     * 添加新的活动实例
     */
    public void addNewActivity(WfmActivity wfmActivity) {
        mapper.insert(wfmActivity);
    }


    /**
     * 根据通知内容更新流程图状态
     *
     * @param clientRequestPostEntity 通知内容,data内容格式:{受理编号:"",业务类型:"",步骤:"",状态:""}
     */
    public void updateActivityStepStatus(ClientRequestPostEntity clientRequestPostEntity) {
        try {
            // 前置步骤，验签
            UserKeyInfo userKeyInfo = userKeyInfoBiz.selectSymmetricPubkeyByUserIdentityId(clientRequestPostEntity.getToken().getUserID()); // 获取密钥信息
            String signPubKey = userKeyInfo.getSignPubKey();
            boolean verifyResult = CryptographyOperation.signatureVerify(signPubKey, clientRequestPostEntity.getData(), clientRequestPostEntity.getSign());
            if (Objects.equals(verifyResult, false))
                throw new ZtgeoBizRuntimeException(CodeMsg.SIGN_ERROR);
            // 查询id对应的密钥信息
            String aesKeyToCorp = userKeyInfo.getSymmetricPubkey();
            String encryptData = CryptographyOperation.aesEncrypt(aesKeyToCorp, clientRequestPostEntity.getData());

            encryptData = URLEncoder.encode(encryptData, StandardCharsets.UTF_8.toString());
            JSONObject noticeJson = JSONObject.parseObject(encryptData);
            String businessNo = noticeJson.getString("businessNo");
            String businessType = noticeJson.getString("businessType");
            String step = noticeJson.getString("step");
            int status = noticeJson.getIntValue("status");
            WfmActivity wfmActivity = mapper.selectActivityByPK(businessNo);
            String imageJsonStr = wfmActivity.getImageJson();
            String imageJson = updateStepStatus(imageJsonStr,step,status);
            wfmActivity.setImageJson(imageJson);
            mapper.updateByPrimaryKeySelective(wfmActivity);
            // 发送通知给别的机构 {受理编号:"",业务类型:"",步骤:"",状态:""}
            System.out.println("待发送信息:"+wfmActivity.toString());
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("解密失败");
        }




    }

    /**
     * 更改流程图的步骤状态,status为0则为蓝色，为1则为红色
     *
     * @param imageJsonStr 流程图
     * @param stepName 步骤名称
     * @param status 状态(0或者1)
     * @return 新的流程图String
     */
    public String updateStepStatus(String imageJsonStr,String stepName,int status){
        JSONObject imageJson = JSONObject.parseObject(imageJsonStr);
        JSONArray nodeDataArray = imageJson.getJSONArray("nodeDataArray");
        JSONArray linkDataArray = imageJson.getJSONArray("linkDataArray");
        int stepKey = 0;
        int findFlag = 0;
        for (int i = 0; i < nodeDataArray.size(); i++) {
            JSONObject nodeJson = nodeDataArray.getJSONObject(i);
            if(Objects.equals(nodeJson.getString("text"),stepName)){
                stepKey = nodeJson.getIntValue("key");
                findFlag = 1;
                break;
            }
        }
        if (Objects.equals(findFlag,0))
            throw new ZtgeoBizRuntimeException(CodeMsg.SDK_PARAM_ERROR,"步骤名称错误");
        for (int i = 0; i < linkDataArray.size(); i++) {
            JSONObject linkJson = linkDataArray.getJSONObject(i);
            if (Objects.equals(stepKey,linkJson.getIntValue("from")) && Objects.equals(status,0)){
                linkJson.put("color","blue");
            } else if(Objects.equals(stepKey,linkJson.getIntValue("from")) && Objects.equals(status,1)){
                linkJson.put("color","red");
            }
        }
        return imageJson.toJSONString();
    }


}