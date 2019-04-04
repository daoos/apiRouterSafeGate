package com.ztgeo.biz;

import org.springframework.stereotype.Service;

import com.ztgeo.entity.NoticeRecord;
import com.ztgeo.mapper.NoticeRecordMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 通知记录
 *
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-09-17 11:52:26
 */
@Service
public class NoticeRecordBiz extends BusinessBiz<NoticeRecordMapper,NoticeRecord> {
}