package com.ztgeo.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ztgeo.entity.WfmProcess;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

/**
 * 模板表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
public interface WfmProcessMapper extends CommonMapper<WfmProcess> {

    WfmProcess selectAllProcessByName(@Param("businessTypeName") String businessTypeName);
}
