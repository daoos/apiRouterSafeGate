package com.ztgeo.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ztgeo.entity.NoticeBaseInfo;
import com.ztgeo.entity.NoticeUserRel;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 通知用户关系表. 表示用户的通知该转发给哪些已配置过地址的用户
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-09-14 11:58:02
 */
public interface NoticeUserRelMapper extends CommonMapper<NoticeUserRel> {

    @Select({"select notice_path noticePath,nbi.user_real_id userRealId,nbi.username username,nbi.name name,uki.user_real_id from notice_base_info nbi inner join notice_user_rel nur on nbi.notice_id = nur.notice_id inner join user_key_info uki on nur.user_real_id = uki.user_real_id where uki.user_identity_id = #{userID} and nur.type_id = #{typeID}"})
    @ResultType(NoticeBaseInfo.class)
    List<NoticeBaseInfo> getNoticeURLList(@Param("userID") String userID, @Param("typeID") String typeID);

    @Select("select type_desc from notice_type_info where type_id=#{typeID} and crt_user_id=#{crtUserId}")
     String  getNoticetype( @Param("typeID") String typeID,@Param("crtUserId") String crtUserId);
    @Insert("insert into notice_record(record_id,sender_id,receiver_id,receiver_url,receiver_usename,receiver_name,typedesc,status,send_time,count,request_data) values(#{recordId},#{senderId},#{receiverId},#{receiverUrl},#{receiverUseName},#{receiverName},#{status},#{sendTime},#{count},#{requestData})")
    void  InsertNoticeRecord(@Param("recordId") String recordId, @Param("senderId") String senderId,@Param("receiverId") String receiverId,@Param("receiverUrl") String receiverUrl, @Param("receiverUseName") String receiverUseName,@Param("receiverName") String receiverName,
                              @Param("typedesc") String typedesc,@Param("status") int status,@Param("sendTime") String sendTime,@Param("count") int count,@Param("requestData") String requestData);

}
