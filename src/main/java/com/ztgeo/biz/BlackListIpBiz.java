package com.ztgeo.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ztgeo.entity.BlackListIp;
import com.ztgeo.mapper.BlackListIpMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;
import java.util.Objects;

/**
 * 黑名单IP表
 *
 * @author zoupeidong
 * @version 2018-09-03 14:53:45
 * @email 806316372@qq.com
 */
@Service
public class BlackListIpBiz extends BusinessBiz<BlackListIpMapper, BlackListIp> {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${customAttributes.black-ip-list-key}")
    private String redisBlackIpListKey;

    /**
     * 检测当前IP是否在黑名单中
     *
     * @param ip 用户真实IP
     * @return 若在黑名单中，则返回true，否则返回false
     */
    public boolean checkIpInBlackList(String ip) {
        // null值检测
        if (Objects.equals(null, ip)) {
            return false;
        }
        // 获取redis缓存黑名单数据
        List<String> list = redisTemplate.opsForList().range(redisBlackIpListKey, 0, -1);
        return list.contains(ip) ? true : false;
    }

    /**
     * 查找所有黑名单IP
     *
     * @return 黑名单IP列表
     */
    public List<String> selectAllBlackIp() {
        return mapper.selectAllBlackIp();
    }
}