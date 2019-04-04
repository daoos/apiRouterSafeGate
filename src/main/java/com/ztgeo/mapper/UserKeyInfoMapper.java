package com.ztgeo.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ztgeo.entity.UserKeyInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

/**
 * Create by Wei on 2018/9/5
 */
public interface UserKeyInfoMapper extends CommonMapper<UserKeyInfo> {

    @Select({"select user_identity_id from user_key_info where user_real_id = #{userId}"})
    @ResultType(String.class)
    String selectUserIdentityIdByUserId(@Param("userId") String userId);

    // 通过用户身份标识查询密钥信息
    UserKeyInfo selectSymmetricPubkeyByUserIdentityId(@Param("userIdentityId") String userIdentityId);

    // 根据APIpubkey查询该API对应的机构的密钥信息
    UserKeyInfo selectKeyInfoByApiId(@Param("apiID") String apiID);

    @Select({"select username from user_key_info where user_identity_id = #{userPubkey}"})
    @ResultType(String.class)
    String selectUsernameByUserPubKey(@Param("userPubkey") String userPubkey);
}
