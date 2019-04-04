package com.ztgeo.mapper;

import com.ztgeo.entity.ApiAuthRel;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-08-27 16:57:29
 */
public interface ApiAuthRelMapper extends CommonMapper<ApiAuthRel> {

    // 查询指定用户是否有权限
    @Select("select count(*) from api_auth_rel aar where aar.user_pubkey=#{id} and aar.api_pubkey=#{apiPubKey}")
    int selectUserAuthByUserIdAndApiId(@Param("id") String id, @Param("apiPubKey") String apiPubKey);

}
