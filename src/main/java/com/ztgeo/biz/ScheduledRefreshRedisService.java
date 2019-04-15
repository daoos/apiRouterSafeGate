package com.ztgeo.biz;

import com.alibaba.fastjson.JSONObject;
import com.ztgeo.common.ZtgeoBizRuntimeException;
import com.ztgeo.crypto.CommonResponseEntity;
import com.ztgeo.crypto.HttpOperation;
import com.ztgeo.entity.ApiAccessRecord;
import com.ztgeo.entity.NoticeRecord;
import com.ztgeo.msg.CodeMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 用于定时刷新redis的ip黑名单缓存
 */
@Component
public class ScheduledRefreshRedisService {

    private Logger log = LoggerFactory.getLogger(ScheduledRefreshRedisService.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private BlackListIpBiz blackListIpBiz;
    @Autowired
    private ApiAccessRecordBiz apiAccessRecordBiz;
    @Autowired
    private NoticeRecordBiz noticeRecordBiz;
    @Value("${customAttributes.black-ip-list-key}")
    private String redisBlackIpListKey;

    // 每天下午17：30执行
    @Scheduled(cron = "0 30 17 * * ?")
    public void refreshIpBlackList() {
        log.info("==========刷新redis黑名单列表缓存,时间:{}", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        // 查询数据库里的黑名单列表数据
        List<String> listIps = blackListIpBiz.selectAllBlackIp();
        // 删除redis里所有黑名单数据
        redisTemplate.delete(redisBlackIpListKey);
        // 添加黑名单列表数据
        redisTemplate.opsForList().rightPushAll(redisBlackIpListKey, listIps);
    }

    @Scheduled(fixedRate = 500000)
    public void sendInterfaceNoticeAgain() {
        List<ApiAccessRecord> listLogs = apiAccessRecordBiz.selectAllLogs();
        System.out.println(listLogs);
        for (int i = 0; i < listLogs.size(); i++) {
            try {
                String rspData = HttpOperation.sendJsonHttp(listLogs.get(i).getApiUrl(), listLogs.get(i).getRequestData());
                CommonResponseEntity commonResponseEntity = JSONObject.parseObject(rspData, CommonResponseEntity.class);
                if (com.ztgeo.utils.StringUtils.isBlank(rspData)) {
                    throw new ZtgeoBizRuntimeException(CodeMsg.FAIL, "未接收到响应数据");
                } else {
                    if (commonResponseEntity.getCode() == 200) {
                        apiAccessRecordBiz.updateApiRecordStatusSuccess(listLogs.get(i).getId());
                    } else {
                        int count = listLogs.get(i).getCount() + 1;
                        apiAccessRecordBiz.updateApiRecordCount(count, listLogs.get(i).getId());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // 每天下午17：30执行
    @Scheduled(cron = "0 30 17 * * ?")
    public void sendNoticeRestart(){
        List<NoticeRecord> listLogs = noticeRecordBiz.selectNoticeAllLogs();
        System.out.println(listLogs);
        for (int i = 0; i < listLogs.size(); i++) {
            try {
                String rspData = HttpOperation.sendJsonHttp(listLogs.get(i).getReceiverUrl(), listLogs.get(i).getRequestData());
                CommonResponseEntity commonResponseEntity = JSONObject.parseObject(rspData, CommonResponseEntity.class);
                if (com.ztgeo.utils.StringUtils.isBlank(rspData)) {
                    throw new ZtgeoBizRuntimeException(CodeMsg.FAIL, "未接收到响应数据");
                } else {
                    if (commonResponseEntity.getCode() == 200) {
                        noticeRecordBiz.updateNoticeRecordStatusSuccess(listLogs.get(i).getReceiverId());
                    } else {
                        int count = listLogs.get(i).getCount() + 1;
                        noticeRecordBiz.updateNoticeRecordCount(count, listLogs.get(i).getReceiverId());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
