package com.ztgeo.mapper;

import com.ztgeo.entity.NoticeRecord;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 通知记录
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-09-17 11:52:26
 */
public interface NoticeRecordMapper extends CommonMapper<NoticeRecord> {
    @Update("UPDATE notice_record SET status=0 WHERE id=#{id}")
    void updateNoticeRecordStatusSuccess(@Param("id") String id);

    @Update("UPDATE notice_record SET count=#{count} WHERE id=#{id}")
    void updateNoticeRecordCount(@Param("count") int count, @Param("id") String id);
    //查询所有的需要重发的通知记录信息
    List<NoticeRecord> selectNoticeAllLogs();
}
