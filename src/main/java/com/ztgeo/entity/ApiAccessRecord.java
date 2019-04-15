package com.ztgeo.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;



/**
 * API访问记录表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-10-09 14:54:00
 */
@Table(name = "api_access_record")
public class ApiAccessRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键ID
    @Id
    private String id;
	
	    //api主键ID
    @Column(name = "api_id")
    private String apiId;
	
	    //api名称
    @Column(name = "api_name")
    private String apiName;
	
	    //api地址
    @Column(name = "api_url")
    private String apiUrl;

    // api类型，0-安全接口,1-通用接口
	@Column(name = "api_type")
    private int apiType;

    // 访问者IP
	@Column(name = "access_client_ip")
    private String accessClientIp;

	    //访问时间
    @Column(name = "access_year")
    private String accessYear;
	
	    //
    @Column(name = "access_month")
    private String accessMonth;
	
	    //
    @Column(name = "access_day")
    private String accessDay;
	
	    //访问时间
    @Column(name = "access_time")
    private String accessTime;

    // 请求数据
	@Column(name = "request_data")
	private String requestData;

	// 响应数据
	@Column(name = "response_data")
	private String responseData;
	//返回类型
	@Column(name = "response_type")
	private int responseType;

	//请求用户的真实id 既登录用户的id
	@Column(name = "user_real_id")
	private String userRealID;
	//是否成功
	@Column(name = "status")
	private int status;
	//重发次数
	@Column(name = "count")
	private int count;
	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：api主键ID
	 */
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	/**
	 * 获取：api主键ID
	 */
	public String getApiId() {
		return apiId;
	}
	/**
	 * 设置：
	 */
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	/**
	 * 获取：
	 */
	public String getApiName() {
		return apiName;
	}
	/**
	 * 设置：
	 */
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	/**
	 * 获取：
	 */
	public String getApiUrl() {
		return apiUrl;
	}
	/**
	 * 设置：访问时间
	 */
	public void setAccessYear(String accessYear) {
		this.accessYear = accessYear;
	}
	/**
	 * 获取：访问时间
	 */
	public String getAccessYear() {
		return accessYear;
	}
	/**
	 * 设置：
	 */
	public void setAccessMonth(String accessMonth) {
		this.accessMonth = accessMonth;
	}
	/**
	 * 获取：
	 */
	public String getAccessMonth() {
		return accessMonth;
	}
	/**
	 * 设置：
	 */
	public void setAccessDay(String accessDay) {
		this.accessDay = accessDay;
	}
	/**
	 * 获取：
	 */
	public String getAccessDay() {
		return accessDay;
	}

	public String getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

	public String getAccessClientIp() {
		return accessClientIp;
	}

	public void setAccessClientIp(String accessClientIp) {
		this.accessClientIp = accessClientIp;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public int getApiType() {
		return apiType;
	}

	public void setApiType(int apiType) {
		this.apiType = apiType;
	}

	public int getResponseType() {
		return responseType;
	}

	public void setResponseType(int responseType) {
		this.responseType = responseType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getUserRealID() {
		return userRealID;
	}

	public void setUserRealID(String userRealID) {
		this.userRealID = userRealID;
	}


}
