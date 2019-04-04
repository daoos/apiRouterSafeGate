package com.ztgeo.biz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.ztgeo.entity.ApiBodyInfo;
import com.ztgeo.entity.ApiHeaderInfo;
import com.ztgeo.entity.HttpEntity;
import com.ztgeo.msg.CodeMsg;
import com.ztgeo.msg.ResultMap;
import com.ztgeo.rest.RouterController;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ztgeo.entity.ApiBaseInfo;
import com.ztgeo.mapper.ApiBaseInfoMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author zoupeidong
 * @version 2018-08-27 16:57:29
 * @email 806316372@qq.com
 */
@Service
public class ApiBaseInfoBiz extends BusinessBiz<ApiBaseInfoMapper, ApiBaseInfo> {

    private Logger log = LoggerFactory.getLogger(ApiBaseInfoBiz.class);

    @Autowired
    private ApiBodyInfoBiz apiBodyInfoBiz;
    @Autowired
    private ApiBaseInfoBiz apiBaseInfoBiz;
    @Autowired
    private UserKeyInfoBiz userKeyInfoBiz;
    @Autowired
    private MongoClient mongoClient;
    @Value("${customAttributes.dbName}")
    private String dbName; // 存储用户发送数据的数据库名

    /**
     * 查询单条Api详细信息
     */
    public ApiBaseInfo getOneApiBaseInfo(String id) {
        return mapper.getOneApiBaseInfo(id);
    }

    /**
     * 根据API配置的参数规则依次验证header
     *
     * @param apiID   API标识
     * @param request HttpServletRequest
     * @return 如果全部验证通过，则返回null，否则返回错误信息
     */
    public String checkHeaders(String apiID, HttpServletRequest request) {
        ApiBaseInfo apiBaseInfo = apiBaseInfoBiz.selectByApiPubkey(apiID);
        List<ApiHeaderInfo> headerInfoList = apiBaseInfo.getApiHeaderInfoList();
        for (int i = 0; i < headerInfoList.size(); i++) {
            String headerName = headerInfoList.get(i).getHeaderName();
            String headerValue = headerInfoList.get(i).getHeaderValue();
            // 检验是否存在必要的header或 如果headerValue存在，则检验是否与预置的headerValue一致
            if (Objects.equals(null, request.getHeader(headerName)) || (!Objects.equals("", headerValue) && !Objects.equals(request.getHeader(headerName), headerValue))) {
                return ResultMap.error(CodeMsg.HEADER_ERROR).toString();
            }
        }
        return null;
    }

    /**
     * 根据API配置的参数规则依次验证参数
     *
     * @param apiID    API标识
     * @param bodyData 用户传递的数据内容
     * @return 如果全部验证通过，则返回null，否则返回错误信息
     */
    public String checkParams(String apiID, JSONArray bodyData) {
        // 查询该API参数规则，用Map存储key，用value存储正则字符串
        List<ApiBodyInfo> list = apiBodyInfoBiz.getParamValidate(apiID);
        Map<String, String> map = new HashMap<>();
        Set<String> paramsSets = new HashSet<>(); // 参数set
        for (ApiBodyInfo apiBodyInfo : list) {
            map.put(apiBodyInfo.getParamName(), apiBodyInfo.getValidateReg() == null ? "" : apiBodyInfo.getValidateReg());
            paramsSets.add(apiBodyInfo.getParamName());
        }
        //  逐一验证参数
        for (int i = 0; i < bodyData.size(); i++) {
            JSONObject dataJson = bodyData.getJSONObject(i);
            Set<String> dataKeySet = dataJson.keySet(); // 获取所有请求参数key
            if (!checkExtraParams(paramsSets, dataKeySet)) {
                return ResultMap.error(CodeMsg.PARAMS_ERROR, "存在多余参数，请检查参数key值").toString();
            }
            // 检测用户传递的数据中是否有多余的key
            Iterator<String> paramsIt = paramsSets.iterator();
            while (paramsIt.hasNext()) {
                String paramsKey = paramsIt.next();
                String validateReg = map.get(paramsKey); // 参数验证规则
                if (!dataJson.containsKey(paramsKey)) {
                    return ResultMap.error(CodeMsg.PARAMS_ERROR, paramsKey + "参数缺失，请检查参数数据").toString();
                } else if (!Objects.equals(null, validateReg) && !Objects.equals("", validateReg) && !Pattern.matches(validateReg, dataJson.getString(paramsKey))) {
                    return ResultMap.error(CodeMsg.PARAMS_ERROR, paramsKey + "验证不通过，请检查参数数据").toString();
                }
            }
        }
        return null;
    }

    /**
     * 以数据库中配置的规则为基础,验证是否有多余参数
     *
     * @param originSet 数据库中配置的参数name集合
     * @param bodySet   用户传递的数据key集合
     * @return 若有多余参数，则返回false，否则返回true
     */
    private boolean checkExtraParams(Set<String> originSet, Set<String> bodySet) {
        Iterator<String> it = bodySet.iterator();
        while (it.hasNext()) {
            String item = it.next();
            if (!originSet.contains(item)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据apipubkey查询API信息
     */
    public ApiBaseInfo selectByApiPubkey(String apiId) {
        return mapper.selectByApiPubkey(apiId);
    }

    /**
     * 备份用户发送的数据至MongoDB
     *
     * @param userPubkey 用户公开的ID
     * @param resultJson 用户发送的数据内容
     * @param request    request请求，包含url,port,method等信息
     */
    public void backUpUserSendData(String userPubkey, JSONObject resultJson, HttpServletRequest request) {
        // 查询用户的登录名
        String userLoginName = userKeyInfoBiz.selectUsernameByUserPubKey(userPubkey);
        // 以用户登录名为collection名称创建collection
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase mongoDB = mongoClient.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);
        MongoCollection<HttpEntity> collection = mongoDB.getCollection(userLoginName+"_send", HttpEntity.class);
        // 封装参数
        HttpEntity httpEntity = new HttpEntity();
        httpEntity.setMethod(request.getMethod());
        httpEntity.setContentType(request.getContentType());
        LocalDateTime localTime = LocalDateTime.now();
        httpEntity.setYear(localTime.getYear());
        httpEntity.setMonth(localTime.getMonthValue());
        httpEntity.setDay(localTime.getDayOfMonth());
        httpEntity.setHour(localTime.getHour());
        httpEntity.setMinute(localTime.getMinute());
        httpEntity.setSecond(localTime.getSecond());
        httpEntity.setCurrentTime(Instant.now().getEpochSecond());
        // 封装header
        JSONObject headerJson = new JSONObject();
        Enumeration<String> headerEnum = request.getHeaderNames();
        while (headerEnum.hasMoreElements()) {
            String headerName = headerEnum.nextElement();
            if (!Objects.equals(null, headerName)) {
                String headerValue = request.getHeader(headerName);
                headerJson.put(headerName, headerValue);
            }
        }
        httpEntity.setHeaders(headerJson.toJSONString());
        // 封装body
        collection.insertOne(httpEntity);
    }

}