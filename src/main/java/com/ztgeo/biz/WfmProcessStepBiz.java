package com.ztgeo.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ztgeo.entity.WfmProcessStep;
import com.ztgeo.mapper.WfmProcessStepMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模板步骤表
 *
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
@Service
public class WfmProcessStepBiz extends BusinessBiz<WfmProcessStepMapper,WfmProcessStep> {

    /**
     * 根据模板号查询步骤信息
     * @param pId 模板ID
     */
    public List<WfmProcessStep> selectAllStepInfoByPId(String pId) {
        return mapper.selectAllStepInfoByPId(pId);
    }
}