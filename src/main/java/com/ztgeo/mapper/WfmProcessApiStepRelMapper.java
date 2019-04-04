package com.ztgeo.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ztgeo.entity.WfmProcessApiStepRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 流程接口服务关系表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
public interface WfmProcessApiStepRelMapper extends CommonMapper<WfmProcessApiStepRel> {

    List<WfmProcessApiStepRel> selectApiStepRelBySId(@Param("sId") String sId);
}
