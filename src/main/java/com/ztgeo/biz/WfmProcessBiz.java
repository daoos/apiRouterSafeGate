package com.ztgeo.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ztgeo.common.ZtgeoBizRuntimeException;
import com.ztgeo.entity.*;
import com.ztgeo.mapper.WfmProcessMapper;
import com.ztgeo.msg.CodeMsg;
import com.ztgeo.msg.PickUpPlaformMsg;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 模板表
 *
 * @author zoupeidong
 * @version 2018-11-26 15:26:20
 * @email 806316372@qq.com
 */
@Service
public class WfmProcessBiz extends BusinessBiz<WfmProcessMapper, WfmProcess> {

    @Autowired
    private WfmActivityBiz wfmActivityBiz;
    @Autowired
    private WfmActivityStepBiz wfmActivityStepBiz;
    @Autowired
    private WfmActivityApiStepRelBiz wfmActivityApiStepRelBiz;
    @Autowired
    private WfmProcessStepBiz wfmProcessStepBiz;
    @Autowired
    private WfmProcessApiStepRelBiz wfmProcessApiStepRelBiz;

    /**
     * 流程触发，复制模板表至活动表
     *
     * @param businessTypeName 业务类型
     * @param businessNo       业务编号/受理编号
     */
    public boolean copyProcessToActivity(String businessTypeName, String businessNo) {
        // 根据业务类型查询模板表
        WfmProcess wfmProcess = mapper.selectAllProcessByName(businessTypeName);
//        if (Objects.equals(wfmProcess, null)) {
        //模板名称错误或内部数据错误
//        }
        // 复制所有模板属性至流程实例
        WfmActivity wfmActivity = new WfmActivity();
        BeanUtils.copyProperties(wfmProcess, wfmActivity);
        wfmActivity.setCrtTime(new Date());
        wfmActivity.setUpdTime(new Date());
        wfmActivity.setAId(businessNo); // 业务编号即活动编号
        wfmActivityBiz.addNewActivity(wfmActivity);
        // 复制所有步骤信息
        List<WfmProcessStep> wfmProcessStepList = wfmProcessStepBiz.selectAllStepInfoByPId(wfmProcess.getPId()); // 根据模板号查询步骤信息
        for (int i = 0; i < wfmProcessStepList.size(); i++) {
            WfmProcessStep wfmProcessStep = wfmProcessStepList.get(i); // 步骤信息
            WfmActivityStep wfmActivityStep = new WfmActivityStep();
            BeanUtils.copyProperties(wfmProcessStep, wfmActivityStep);
            String activityStepSId = UUIDUtils.generateUuid();
            wfmActivityStep.setSId(activityStepSId); // 步骤信息主键
            wfmActivityStep.setAId(businessNo); // 业务编号即活动编号
            wfmActivityStepBiz.addNewActivityStepInfo(wfmActivityStep);
            // 查询模板步骤对应的api信息
            List<WfmProcessApiStepRel> WfmProcessApiStepRelList = wfmProcessApiStepRelBiz.selectApiStepRelBySId(wfmProcessStep.getSId());
            for (int j = 0; j < WfmProcessApiStepRelList.size(); j++) {
                WfmProcessApiStepRel wfmProcessApiStepRel = WfmProcessApiStepRelList.get(j);
                WfmActivityApiStepRel wfmActivityApiStepRel = new WfmActivityApiStepRel();
                BeanUtils.copyProperties(wfmProcessApiStepRel, wfmActivityApiStepRel);
                wfmActivityApiStepRel.setRelId(UUIDUtils.generateUuid());
                wfmActivityApiStepRel.setSId(activityStepSId);
                wfmActivityApiStepRelBiz.addNewActivityApiStepRel(wfmActivityApiStepRel);
            }
        }
        return true;
    }

    /**
     * 流程触发，复制模板表至活动表
     *
     * @param businessTypeName 业务类型
     * @param businessNo       业务编号/受理编号
     */
    public void createSimpleActivity(String businessTypeName, String businessNo) {
        try {
            // 根据业务类型查询模板表
            WfmProcess wfmProcess = mapper.selectAllProcessByName(businessTypeName);
            if (Objects.equals(null, wfmProcess)) {
                throw new ZtgeoBizRuntimeException(CodeMsg.PARAMS_ERROR, "业务类型名称错误，未查找到模板");
            }
            // 复制所有模板属性至流程实例
            WfmActivity wfmActivity = new WfmActivity();
            BeanUtils.copyProperties(wfmProcess, wfmActivity);
            String imageJson = wfmActivityBiz.updateStepStatus(wfmActivity.getImageJson(),"开始",0);
            wfmActivity.setImageJson(imageJson);
            wfmActivity.setCrtTime(new Date());
            wfmActivity.setUpdTime(new Date());
            wfmActivity.setAId(businessNo); // 业务编号即活动编号
            wfmActivityBiz.addNewActivity(wfmActivity);
        } catch (DuplicateKeyException e) {
            throw new ZtgeoBizRuntimeException(CodeMsg.FAIL, "受理编号重复，请检查数据");
        }
    }

}