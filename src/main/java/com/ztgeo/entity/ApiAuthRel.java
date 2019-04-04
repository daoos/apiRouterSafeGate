package com.ztgeo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-08-27 19:05:19
 */
@Table(name = "api_auth_rel")
public class ApiAuthRel implements Serializable {
	private static final long serialVersionUID = 1L;

	    //权限关系id
    @Id
    private String relId;
	
	    //机构ID(用户ID)的MD5字符串
    @Column(name = "user_pubkey")
    private String userPubkey;
	
	    //服务ID的MD5字符串
    @Column(name = "api_pubkey")
    private String apiPubkey;
	

	/**
	 * 设置：权限关系id
	 */
	public void setRelId(String relId) {
		this.relId = relId;
	}
	/**
	 * 获取：权限关系id
	 */
	public String getRelId() {
		return relId;
	}
	/**
	 * 设置：机构ID(用户ID)的MD5字符串
	 */
	public void setUserPubkey(String userPubkey) {
		this.userPubkey = userPubkey;
	}
	/**
	 * 获取：机构ID(用户ID)的MD5字符串
	 */
	public String getUserPubkey() {
		return userPubkey;
	}
	/**
	 * 设置：服务ID的MD5字符串
	 */
	public void setApiPubkey(String apiPubkey) {
		this.apiPubkey = apiPubkey;
	}
	/**
	 * 获取：服务ID的MD5字符串
	 */
	public String getApiPubkey() {
		return apiPubkey;
	}
}
