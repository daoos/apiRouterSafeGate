package com.ztgeo.mapper;

import com.ztgeo.entity.BlackListIp;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 黑名单IP表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-09-03 14:53:45
 */
public interface BlackListIpMapper extends CommonMapper<BlackListIp> {

    @Select("select inet_ntoa(ip_content) from black_list_ip")
    List<String> selectAllBlackIp();
}
