package com.ztgeo.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ztgeo.entity.NoticeBaseInfo;
import com.ztgeo.entity.NoticeUserRel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 通知用户关系表. 表示用户的通知该转发给哪些已配置过地址的用户
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-09-14 11:58:02
 */
public interface NoticeUserRelMapper extends CommonMapper<NoticeUserRel> {

    @Select({"select notice_path noticePath,nbi.user_real_id userRealId,nbi.username username,nbi.name name from notice_base_info nbi inner join notice_user_rel nur on nbi.notice_id = nur.notice_id inner join user_key_info uki on nur.user_real_id = uki.user_real_id where uki.user_identity_id = #{userID} and nur.type_id = #{typeID}"})
    @ResultType(NoticeBaseInfo.class)
    List<NoticeBaseInfo> getNoticeURLList(@Param("userID") String userID, @Param("typeID") String typeID);

    @Select("select type_desc from notice_type_info where type_id=#{typeID}")
     String  getNoticetype( @Param("typeID") String typeID);
}
