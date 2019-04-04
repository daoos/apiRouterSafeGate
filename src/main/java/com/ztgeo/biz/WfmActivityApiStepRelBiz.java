package com.ztgeo.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ztgeo.entity.WfmActivityApiStepRel;
import com.ztgeo.mapper.WfmActivityApiStepRelMapper;
import org.springframework.stereotype.Service;

/**
 * 活动步骤和API关系表
 *
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
@Service
public class WfmActivityApiStepRelBiz extends BusinessBiz<WfmActivityApiStepRelMapper,WfmActivityApiStepRel> {
    public void addNewActivityApiStepRel(WfmActivityApiStepRel wfmActivityApiStepRel) {
        mapper.insert(wfmActivityApiStepRel);
    }
}