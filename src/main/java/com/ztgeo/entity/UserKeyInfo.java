package com.ztgeo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 用户密钥表
 * 
 * @author Mr.AG
 * @email 1205690873@qq.com
 * @version 2018-09-03 15:49:50
 */
@Table(name = "user_key_info")
public class UserKeyInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String keyId;
	
	    //用户真实ID
    @Column(name = "user_real_id")
    private String userRealId;

	// 用户登录名
	@Column(name = "username")
	private String username;

	@Transient
	private String apiName;
	@Transient
	private String baseUrl;
	@Transient
	private String path;
	// 用户名称
	@Column(name = "name")
	private String name;

	    //用户身份标识(由userid经过哈希获得)
    @Column(name = "user_identity_id")
    private String userIdentityId;
	
	    //对称公钥，用于数据加密
    @Column(name = "symmetric_pubkey")
    private String symmetricPubkey;
	
	    //私钥，用于对数据签名
    @Column(name = "sign_secret_key")
    private String signSecretKey;

        //公钥，存放于平台中,用于平台返回数据的客户端验签
    @Column(name = "sign_pub_key")
    private String signPubKey;
	
	    //记录创建时间
    @Column(name = "crt_time")
    private Date crtTime;
	
	    //记录创建者ID
    @Column(name = "crt_user_id")
    private String crtUserId;
	
	    //记录修改时间
    @Column(name = "upd_time")
    private Date updTime;
	
	    //记录修改者ID
    @Column(name = "upd_user_id")
    private String updUserId;
	

	/**
	 * 设置：
	 */
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	/**
	 * 获取：
	 */
	public String getKeyId() {
		return keyId;
	}
	/**
	 * 设置：用户真实ID
	 */
	public void setUserRealId(String userRealId) {
		this.userRealId = userRealId;
	}
	/**
	 * 获取：用户真实ID
	 */
	public String getUserRealId() {
		return userRealId;
	}
	/**
	 * 设置：用户身份标识(由userid经过哈希获得)
	 */
	public void setUserIdentityId(String userIdentityId) {
		this.userIdentityId = userIdentityId;
	}
	/**
	 * 获取：用户身份标识(由userid经过哈希获得)
	 */
	public String getUserIdentityId() {
		return userIdentityId;
	}
	/**
	 * 设置：对称公钥，用于数据加密
	 */
	public void setSymmetricPubkey(String symmetricPubkey) {
		this.symmetricPubkey = symmetricPubkey;
	}
	/**
	 * 获取：对称公钥，用于数据加密
	 */
	public String getSymmetricPubkey() {
		return symmetricPubkey;
	}
	/**
	 * 设置：私钥，用于对数据签名
	 */
	public void setSignSecretKey(String signSecretKey) {
		this.signSecretKey = signSecretKey;
	}
	/**
	 * 获取：私钥，用于对数据签名
	 */
	public String getSignSecretKey() {
		return signSecretKey;
	}
	/**
	 * 设置：记录创建时间
	 */
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	/**
	 * 获取：记录创建时间
	 */
	public Date getCrtTime() {
		return crtTime;
	}
	/**
	 * 设置：记录创建者ID
	 */
	public void setCrtUserId(String crtUserId) {
		this.crtUserId = crtUserId;
	}
	/**
	 * 获取：记录创建者ID
	 */
	public String getCrtUserId() {
		return crtUserId;
	}
	/**
	 * 设置：记录修改时间
	 */
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	/**
	 * 获取：记录修改时间
	 */
	public Date getUpdTime() {
		return updTime;
	}
	/**
	 * 设置：记录修改者ID
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
	/**
	 * 获取：记录修改者ID
	 */
	public String getUpdUserId() {
		return updUserId;
	}

	public String getSignPubKey() {
		return signPubKey;
	}

	public void setSignPubKey(String signPubKey) {
		this.signPubKey = signPubKey;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
