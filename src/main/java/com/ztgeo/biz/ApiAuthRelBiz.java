package com.ztgeo.biz;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import com.ztgeo.entity.ApiAuthRel;
import com.ztgeo.mapper.ApiAuthRelMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 *
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-08-27 16:57:29
 */
@Service
public class ApiAuthRelBiz extends BusinessBiz<ApiAuthRelMapper,ApiAuthRel> {

    /**
     *  判断用户token是否过期以及是否有权限访问对应API
     *  @param tokenJson 用户传递的token数据
     *  @return 用户token未过期且有权限访问对应API，返回apiId，否则返回null
     */
    public boolean checkUserToken(JSONObject tokenJson) {
        // 获取请求发送时间
        long sendTime = tokenJson.getLong("timestamp");
        // 计算从发送到当前的秒数
        long durationSeconds = Duration.between(Instant.ofEpochMilli(sendTime),Instant.now()).getSeconds();
        // 超过1分钟则超时
        if (durationSeconds >= 60){
            return false;
        }
        // 获取用户ID和ApiID
        String userPubkey = tokenJson.getString("userID");
        String apiPubKey = tokenJson.getString("apiID");
        // 查询该用户是否有权限访问该API
        int userHasAuth = mapper.selectUserAuthByUserIdAndApiId(userPubkey,apiPubKey);
        if(Objects.equals(userHasAuth,0)){ // 没有权限则返回数据0条，否则有权限
            return false;
        }
        return true;
    }
}