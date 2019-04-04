package com.ztgeo.biz;

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
    @Value("${customAttributes.black-ip-list-key}")
    private String redisBlackIpListKey;

    // 每天下午17：30执行
    @Scheduled(cron = "0 30 17 * * ?")
    public void refreshIpBlackList(){
        log.info("==========刷新redis黑名单列表缓存,时间:{}", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        // 查询数据库里的黑名单列表数据
        List<String> listIps = blackListIpBiz.selectAllBlackIp();
        // 删除redis里所有黑名单数据
        redisTemplate.delete(redisBlackIpListKey);
        // 添加黑名单列表数据
        redisTemplate.opsForList().rightPushAll(redisBlackIpListKey, listIps);
    }

}
