package com.ztgeo.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ztgeo.entity.WfmProcessApiStepRel;
import com.ztgeo.mapper.WfmProcessApiStepRelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程接口服务关系表
 *
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
@Service
public class WfmProcessApiStepRelBiz extends BusinessBiz<WfmProcessApiStepRelMapper,WfmProcessApiStepRel> {

    /**
     * 查询模板步骤对应的api信息
     *
     * @param sId 步骤信息表主键
     * @return 步骤对应的API信息列表
     */
    public List<WfmProcessApiStepRel> selectApiStepRelBySId(String sId) {
        return mapper.selectApiStepRelBySId(sId);
    }
}