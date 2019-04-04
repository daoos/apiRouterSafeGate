package com.ztgeo.biz;

import org.springframework.stereotype.Service;

import com.ztgeo.entity.ApiBodyInfo;
import com.ztgeo.mapper.ApiBodyInfoMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;
import java.util.Map;

@Service
public class ApiBodyInfoBiz extends BusinessBiz<ApiBodyInfoMapper,ApiBodyInfo> {

    /**
     * 查询该API参数规则，用Map存储key，用value存储正则字符串
     */
    public List<ApiBodyInfo> getParamValidate(String apiID) {
        return mapper.getParamValidate(apiID);
    }
}