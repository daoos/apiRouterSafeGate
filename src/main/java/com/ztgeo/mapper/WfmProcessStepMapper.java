package com.ztgeo.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ztgeo.entity.WfmProcessStep;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模板步骤表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
public interface WfmProcessStepMapper extends CommonMapper<WfmProcessStep> {

    List<WfmProcessStep> selectAllStepInfoByPId(@Param("pId") String pId);
}
