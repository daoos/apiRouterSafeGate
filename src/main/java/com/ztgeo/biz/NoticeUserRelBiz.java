package com.ztgeo.biz;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.mongodb.MongoClient;

import com.ztgeo.common.ZtgeoBizRuntimeException;
import com.ztgeo.entity.*;
import com.ztgeo.mapper.NoticeUserRelMapper;
import com.ztgeo.msg.CodeMsg;
import com.ztgeo.msg.ResultMap;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 通知用户关系表. 表示用户的通知该转发给哪些已配置过地址的用户
 *
 * @author zoupeidong
 * @version 2018-09-14 11:58:02
 * @email 806316372@qq.com
 */
@Service
public class NoticeUserRelBiz extends BusinessBiz<NoticeUserRelMapper, NoticeUserRel> {

    @Autowired
    private UserKeyInfoBiz userKeyInfoBiz;
    @Autowired
    private MongoClient mongoClient;
    @Value("${customAttributes.dbnoticeName}")
    private String dbnoticeName; // 存储用户发送数据的数据库名

    /**
     * 群发通知（异步）
     * {httpDataType:"",messageType"",token:"{}",data:"",noticeCode:""}
     */
    public String sendNotice(JSONObject jsonObject, HttpServletRequest requestReceive) {
        // 查询发送者ID和待发送的通知类型
        JSONObject tokenEntityJson = jsonObject.getJSONObject("token");
        //String userID = tokenEntityJson.getString("userID");
        String userID = "35c0854f7a74d150b832075b49ab13a03c721b85";
        String noticeCode = jsonObject.getString("noticeCode");
        String sendStr = jsonObject.getString("data");
        // 查询待发送的http列表
        List<NoticeBaseInfo> urlList = mapper.getNoticeURLList(userID, noticeCode);
        // 异步发送http请求
        for (int i = 0; i < urlList.size(); i++) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();
            RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                    , sendStr);
            //接收方真实ID
            String receiverId=urlList.get(i).getUserRealId();
            //发送地址
            String url = urlList.get(i).getNoticePath();
            //接收方用户名（例如bdc_dj）
            String receiverName = urlList.get(i).getUsername();
            //接收方机构名
            String name = urlList.get(i).getName();
            //String typedesc = mapper.getNoticetype(noticeCode,urlList.get(i).getUserId());
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String currentTime = dateTimeFormatter.format(localDateTime);
            Request request = new Request.Builder()
                    .url(url)//请求的url
                    .post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    System.out.println("11");
                    mapper.InsertNoticeRecord(UUIDUtils.generateShortUuid(),userID,receiverId,url,receiverName,name,noticeCode,
                            1,currentTime,0,sendStr);
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    response.body().string();//
                    System.out.println("22");
                    if (response.isSuccessful()) { // 成功响应

                        mapper.InsertNoticeRecord(UUIDUtils.generateShortUuid(),userID,receiverId,url,receiverName,name,noticeCode,
                                0,currentTime,0,sendStr);
                    } else { // 失败
                        System.out.println("33");
                        mapper.InsertNoticeRecord(UUIDUtils.generateShortUuid(),userID,receiverId,url,receiverName,name,noticeCode,
                                1,currentTime,0,sendStr);
                        throw new ZtgeoBizRuntimeException(CodeMsg.RECEIVE_EXCEPTION, "请联系相关人员");
                    }

                }
            });
        }
        return ResultMap.ok(CodeMsg.PROCESS_SUCCESS).toString();
    }

}