package com.ztgeo.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * 
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-08-27 16:57:29
 */
@Table(name = "api_base_info")
public class ApiBaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键ID
    @Id
    private String apiId;
	
	    //公开的服务ID
    @Column(name = "api_pubkey")
    private String apiPubkey;
	
	    //api名称
    @Column(name = "api_name")
    private String apiName;
	
	    //基础URL
    @Column(name = "base_url")
    private String baseUrl;
	
	    //后缀
    @Column(name = "path")
    private String path;
	
	    //接口method
    @Column(name = "method")
    private String method;
	
	    //启用状态,0启用,1未启用
    @Column(name = "enabled_status")
    private Boolean enabledStatus;
	
	    //所属机构ID
    @Column(name = "api_owner_id")
    private String apiOwnerId;
	
	    //所属机构名称
    @Column(name = "api_owner_name")
    private String apiOwnerName;
	
	    //创建者ID
    @Column(name = "crt_user_id")
    private String crtUserId;
	
	    //创建时间
    @Column(name = "crt_time")
    private Date crtTime;
	
	    //修改者ID
    @Column(name = "upd_user_id")
    private String updUserId;
	
	    //修改时间
    @Column(name = "upd_time")
    private Date updTime;

	//接口类型
	@Column(name = "api_type")
	private int apiType;
    // header列表
	@Transient
	private List<ApiHeaderInfo> apiHeaderInfoList;
	// query列表
	@Transient
	private List<ApiQueryInfo> apiQueryInfoList;
	// body参数列表
	@Transient
	private List<ApiBodyInfo> apiBodyInfoList;

	/**
	 * 设置：主键ID
	 */
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	/**
	 * 获取：主键ID
	 */
	public String getApiId() {
		return apiId;
	}
	/**
	 * 设置：公开的服务ID
	 */
	public void setApiPubkey(String apiPubkey) {
		this.apiPubkey = apiPubkey;
	}
	/**
	 * 获取：公开的服务ID
	 */
	public String getApiPubkey() {
		return apiPubkey;
	}
	/**
	 * 设置：api名称
	 */
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	/**
	 * 获取：api名称
	 */
	public String getApiName() {
		return apiName;
	}
	/**
	 * 设置：基础URL
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	/**
	 * 获取：基础URL
	 */
	public String getBaseUrl() {
		return baseUrl;
	}
	/**
	 * 设置：后缀
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * 获取：后缀
	 */
	public String getPath() {
		return path;
	}
	/**
	 * 设置：接口method
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * 获取：接口method
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * 设置：启用状态,0启用,1未启用
	 */
	public void setEnabledStatus(Boolean enabledStatus) {
		this.enabledStatus = enabledStatus;
	}
	/**
	 * 获取：启用状态,0启用,1未启用
	 */
	public Boolean getEnabledStatus() {
		return enabledStatus;
	}
	/**
	 * 设置：所属机构ID
	 */
	public void setApiOwnerId(String apiOwnerId) {
		this.apiOwnerId = apiOwnerId;
	}
	/**
	 * 获取：所属机构ID
	 */
	public String getApiOwnerId() {
		return apiOwnerId;
	}
	/**
	 * 设置：所属机构名称
	 */
	public void setApiOwnerName(String apiOwnerName) {
		this.apiOwnerName = apiOwnerName;
	}
	/**
	 * 获取：所属机构名称
	 */
	public String getApiOwnerName() {
		return apiOwnerName;
	}
	/**
	 * 设置：创建者ID
	 */
	public void setCrtUserId(String crtUserId) {
		this.crtUserId = crtUserId;
	}
	/**
	 * 获取：创建者ID
	 */
	public String getCrtUserId() {
		return crtUserId;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public int getApiType() {
		return apiType;
	}

	public void setApiType(int apiType) {
		this.apiType = apiType;
	}

	/**
	 * 设置：修改者ID
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
	/**
	 * 获取：修改者ID
	 */
	public String getUpdUserId() {
		return updUserId;
	}

	public List<ApiHeaderInfo> getApiHeaderInfoList() {
		return apiHeaderInfoList;
	}

	public void setApiHeaderInfoList(List<ApiHeaderInfo> apiHeaderInfoList) {
		this.apiHeaderInfoList = apiHeaderInfoList;
	}

	public List<ApiQueryInfo> getApiQueryInfoList() {
		return apiQueryInfoList;
	}

	public void setApiQueryInfoList(List<ApiQueryInfo> apiQueryInfoList) {
		this.apiQueryInfoList = apiQueryInfoList;
	}

	public List<ApiBodyInfo> getApiBodyInfoList() {
		return apiBodyInfoList;
	}

	public void setApiBodyInfoList(List<ApiBodyInfo> apiBodyInfoList) {
		this.apiBodyInfoList = apiBodyInfoList;
	}

	@Override
	public String toString() {
		return "ApiBaseInfo{" +
				"apiId='" + apiId + '\'' +
				", apiPubkey='" + apiPubkey + '\'' +
				", apiName='" + apiName + '\'' +
				", baseUrl='" + baseUrl + '\'' +
				", path='" + path + '\'' +
				", method='" + method + '\'' +
				", enabledStatus=" + enabledStatus +
				", apiOwnerId='" + apiOwnerId + '\'' +
				", apiOwnerName='" + apiOwnerName + '\'' +
				", crtUserId='" + crtUserId + '\'' +
				", crtTime=" + crtTime +
				", updUserId='" + updUserId + '\'' +
				", updTime=" + updTime +
				", apiType='" + apiType + '\'' +
				", apiHeaderInfoList=" + apiHeaderInfoList +
				", apiQueryInfoList=" + apiQueryInfoList +
				", apiBodyInfoList=" + apiBodyInfoList +
				'}';
	}
}
