package com.ztgeo.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ztgeo.entity.ApiAccessRecord;
import com.ztgeo.entity.NoticeRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * API访问记录表
 *
 * @author zoupeidong
 * @version 2018-10-09 14:54:00
 * @email 806316372@qq.com
 */
public interface ApiAccessRecordMapper extends CommonMapper<ApiAccessRecord> {
    @Update("UPDATE api_access_record SET status=1,count=0 WHERE id=#{id}")
    void updateApiRecordStatus(@Param("id") String id);

    @Update("UPDATE api_access_record SET status=0 WHERE id=#{id}")
    void updateApiRecordStatusSuccess(@Param("id") String id);

    @Update("UPDATE api_access_record SET count=#{count} WHERE id=#{id}")
    void updateApiRecordCount(@Param("count") int count, @Param("id") String id);

    //查询所有的需要重发的接口记录信息
    List<ApiAccessRecord> selectAllLogs();


}
