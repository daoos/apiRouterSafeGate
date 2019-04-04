package com.ztgeo.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ztgeo.entity.WfmActivityStep;
import com.ztgeo.mapper.WfmActivityStepMapper;
import org.springframework.stereotype.Service;

/**
 * 活动步骤表
 *
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
@Service
public class WfmActivityStepBiz extends BusinessBiz<WfmActivityStepMapper,WfmActivityStep> {

    /**
     * 复制所有步骤信息
     */
    public void addNewActivityStepInfo(WfmActivityStep wfmActivityStep) {
        mapper.insert(wfmActivityStep);
    }
}