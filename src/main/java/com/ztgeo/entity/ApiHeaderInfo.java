package com.ztgeo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-08-27 16:57:29
 */
@Table(name = "api_header_info")
public class ApiHeaderInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //header表id
    @Id
    private String headerId;
	
	    //api主键
    @Column(name = "api_id")
    private String apiId;
	
	    //header的name值
    @Column(name = "header_name")
    private String headerName;
	
	    //header的value值
    @Column(name = "header_value")
    private String headerValue;
	
	    //备注
    @Column(name = "header_note")
    private String headerNote;
	
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

    @Transient
    private ApiBaseInfo apiBaseInfo;

	/**
	 * 设置：header表id
	 */
	public void setHeaderId(String headerId) {
		this.headerId = headerId;
	}
	/**
	 * 获取：header表id
	 */
	public String getHeaderId() {
		return headerId;
	}
	/**
	 * 设置：api主键
	 */
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	/**
	 * 获取：api主键
	 */
	public String getApiId() {
		return apiId;
	}
	/**
	 * 设置：header的name值
	 */
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}
	/**
	 * 获取：header的name值
	 */
	public String getHeaderName() {
		return headerName;
	}
	/**
	 * 设置：header的value值
	 */
	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}
	/**
	 * 获取：header的value值
	 */
	public String getHeaderValue() {
		return headerValue;
	}
	/**
	 * 设置：备注
	 */
	public void setHeaderNote(String headerNote) {
		this.headerNote = headerNote;
	}
	/**
	 * 获取：备注
	 */
	public String getHeaderNote() {
		return headerNote;
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

	public ApiBaseInfo getApiBaseInfo() {
		return apiBaseInfo;
	}

	public void setApiBaseInfo(ApiBaseInfo apiBaseInfo) {
		this.apiBaseInfo = apiBaseInfo;
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

	@Override
	public String toString() {
		return "ApiHeaderInfo{" +
				"headerId='" + headerId + '\'' +
				", apiId='" + apiId + '\'' +
				", headerName='" + headerName + '\'' +
				", headerValue='" + headerValue + '\'' +
				", headerNote='" + headerNote + '\'' +
				", crtUserId='" + crtUserId + '\'' +
				", crtTime=" + crtTime +
				", updUserId='" + updUserId + '\'' +
				", updTime=" + updTime +
				", apiBaseInfo=" + apiBaseInfo +
				'}';
	}
}
