package com.ztgeo.mapper;

import com.ztgeo.entity.ApiBodyInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-08-27 16:57:29
 */
public interface ApiBodyInfoMapper extends CommonMapper<ApiBodyInfo> {

    // 查询API的body参数及校验规则
    List<ApiBodyInfo> getParamValidate(@Param("apiId") String apiId);
}
