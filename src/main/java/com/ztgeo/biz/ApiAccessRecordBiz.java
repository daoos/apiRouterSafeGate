package com.ztgeo.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ztgeo.entity.ApiAccessRecord;
import com.ztgeo.entity.ApiBaseInfo;
import com.ztgeo.entity.NoticeRecord;
import com.ztgeo.mapper.ApiAccessRecordMapper;
import com.ztgeo.utils.HttpUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * API访问记录表
 *
 * @author zoupeidong
 * @version 2018-10-09 14:54:00
 * @email 806316372@qq.com
 */
@Service
public class ApiAccessRecordBiz extends BusinessBiz<ApiAccessRecordMapper, ApiAccessRecord> {

    /**
     * 记录安全接口的访问数据
     *
     * @param request     httpServeltRequest对象
     * @param apiBaseInfo 接口信息
     * @param encryptData
     * @return 返回主键
     */
    public String insertApiRecord(HttpServletRequest request, ApiBaseInfo apiBaseInfo, String encryptData) {
        String ipStr = HttpUtils.getIpAdrress(request); // 访问者IP
        ApiAccessRecord apiAccessRecord = new ApiAccessRecord();
        String id = UUIDUtils.generateShortUuid();
        apiAccessRecord.setId(id);
        apiAccessRecord.setApiId(apiBaseInfo.getApiId());
        apiAccessRecord.setApiName(apiBaseInfo.getApiName());
        apiAccessRecord.setApiUrl(apiBaseInfo.getBaseUrl() + apiBaseInfo.getPath());
        apiAccessRecord.setApiType(0);
        apiAccessRecord.setAccessClientIp(ipStr);
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateTimeFormatter.format(localDateTime);
        apiAccessRecord.setAccessTime(currentTime);
        apiAccessRecord.setAccessYear(String.valueOf(localDateTime.getYear()));
        apiAccessRecord.setAccessMonth(String.valueOf(localDateTime.getMonth().getValue()));
        apiAccessRecord.setAccessDay(String.valueOf(localDateTime.getDayOfMonth()));
        apiAccessRecord.setRequestData(encryptData);
        apiAccessRecord.setApiType(apiBaseInfo.getApiType());
        apiAccessRecord.setUserRealID(apiBaseInfo.getApiOwnerId());
        mapper.insertSelective(apiAccessRecord);
        return id;
    }

    /**
     * 存储响应数据
     *
     * @param primaryKey 主键
     * @param rspData    响应的加密的数据
     */
    public void updateApiRecord(String primaryKey, String rspData) {
        ApiAccessRecord apiAccessRecord = new ApiAccessRecord();
        apiAccessRecord.setId(primaryKey);
        apiAccessRecord.setResponseData(rspData);
        mapper.updateByPrimaryKeySelective(apiAccessRecord);
    }
    public void updateApiRecordStatus(String id) {
        mapper.updateApiRecordStatus(id);
    }
    public void updateApiRecordStatusSuccess(String id) {
        mapper.updateApiRecordStatusSuccess(id);
    }
    public void updateApiRecordCount(int count,String id) {
        mapper.updateApiRecordCount(count,id);
    }
    public List<ApiAccessRecord> selectAllLogs() {
        return mapper.selectAllLogs();
    }

}