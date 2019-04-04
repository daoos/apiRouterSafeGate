package com.ztgeo.biz;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.ztgeo.common.ZtgeoBizRuntimeException;
import com.ztgeo.entity.*;
import com.ztgeo.mapper.NoticeUserRelMapper;
import com.ztgeo.msg.CodeMsg;
import com.ztgeo.msg.ResultMap;
import okhttp3.*;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
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
        String userID = tokenEntityJson.getString("userID");
        String noticeCode = jsonObject.getString("noticeCode");
        String sendStr = jsonObject.getString("data");
        // 保存发送的通知数据sendStr至MongoDb
        String userLoginName = userKeyInfoBiz.selectUsernameByUserPubKey(userID);// 查询用户的登录名
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase mongoDB = mongoClient.getDatabase(dbnoticeName).withCodecRegistry(pojoCodecRegistry);
        MongoCollection<NoticeEntity> sendCollection = mongoDB.getCollection(userLoginName + "_noticesend", NoticeEntity.class);
        // 查询待发送的http列表
        List<NoticeBaseInfo> urlList = mapper.getNoticeURLList(userID, noticeCode);
        System.out.println(urlList);
        // 异步发送http请求
        for (int i = 0; i < urlList.size(); i++) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();
            RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                    , sendStr);
            NoticeEntity noticeEntity = new NoticeEntity();
            noticeEntity.setMethod(requestReceive.getMethod());
            noticeEntity.setContentType(requestReceive.getContentType());
            LocalDateTime localTime = LocalDateTime.now();
            noticeEntity.setYear(localTime.getYear());
            noticeEntity.setMonth(localTime.getMonthValue());
            noticeEntity.setDay(localTime.getDayOfMonth());
            noticeEntity.setHour(localTime.getHour());
            noticeEntity.setMinute(localTime.getMinute());
            noticeEntity.setSecond(localTime.getSecond());
            noticeEntity.setCurrentTime(Instant.now().getEpochSecond());
            noticeEntity.setNoticeCode(noticeCode);
            noticeEntity.setUerId(userID);
            noticeEntity.setBody(sendStr);
            String url = urlList.get(i).getNoticePath();
            String username = urlList.get(i).getUsername();
            Request request = new Request.Builder()
                    .url(url)//请求的url
                    .post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            String name = urlList.get(i).getName();
            String receiverId = urlList.get(i).getUserRealId(); // 接收者ID
            String typedesc = mapper.getNoticetype(noticeCode);
            noticeEntity.setUrl(url);
            noticeEntity.setReceiveId(receiverId);
            noticeEntity.setUsername(username);
            noticeEntity.setTypedesc(typedesc);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    noticeEntity.setName(name);
                    noticeEntity.setStatus(1);
                    sendCollection.insertOne(noticeEntity);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    response.body().string();//
                    System.out.println(noticeEntity);
                    noticeEntity.setName(name);
                    if (response.isSuccessful()) { // 成功响应
                        noticeEntity.setStatus(0);
                        sendCollection.insertOne(noticeEntity);
                    } else { // 失败
                        noticeEntity.setStatus(1);
                        sendCollection.insertOne(noticeEntity);
                        throw new ZtgeoBizRuntimeException(CodeMsg.RECEIVE_EXCEPTION, "请联系相关人员");
                    }

                }
            });
        }
        return ResultMap.ok(CodeMsg.PROCESS_SUCCESS).toString();
    }
}