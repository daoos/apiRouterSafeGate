package com.ztgeo.mapper;

import com.ztgeo.entity.ApiBaseInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-08-27 16:57:29
 */
public interface ApiBaseInfoMapper extends CommonMapper<ApiBaseInfo> {

    // 查询单个API详细信息
    ApiBaseInfo getOneApiBaseInfo(@Param("apiId") String apiId);

    // 根据apipubkey查询API信息
    ApiBaseInfo selectByApiPubkey(@Param("apipubkey") String apiId);
}
