package com.ztgeo.entity;

import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 通知基础信息配置表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-09-14 11:58:02
 */
@Table(name = "notice_base_info")
public class NoticeBaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String noticeId;
	
	    //用户真实ID
    @Column(name = "user_real_id")
    private String userRealId;

	// 用户登录名
	@Column(name = "username")
	private String username;

	    //通知转发路径
    @Column(name = "notice_path")
    private String noticePath;
	
	    //http请求方法
    @Column(name = "method")
    private String method;
	
	    //通知说明
    @Column(name = "notice_note")
    private String noticeNote;
	
	    //
    @Column(name = "crt_time")
    private Date crtTime;
	
	    //
    @Column(name = "crt_user_id")
    private String crtUserId;
	
	    //
    @Column(name = "upd_time")
    private Date updTime;
	
	    //
    @Column(name = "upd_user_id")
    private String updUserId;

    @Transient
    private  String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 设置：
	 */
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	/**
	 * 获取：
	 */
	public String getNoticeId() {
		return noticeId;
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
	 * 设置：通知转发路径
	 */
	public void setNoticePath(String noticePath) {
		this.noticePath = noticePath;
	}
	/**
	 * 获取：通知转发路径
	 */
	public String getNoticePath() {
		return noticePath;
	}
	/**
	 * 设置：http请求方法
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * 获取：http请求方法
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * 设置：通知说明
	 */
	public void setNoticeNote(String noticeNote) {
		this.noticeNote = noticeNote;
	}
	/**
	 * 获取：通知说明
	 */
	public String getNoticeNote() {
		return noticeNote;
	}
	/**
	 * 设置：
	 */
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	/**
	 * 获取：
	 */
	public Date getCrtTime() {
		return crtTime;
	}
	/**
	 * 设置：
	 */
	public void setCrtUserId(String crtUserId) {
		this.crtUserId = crtUserId;
	}
	/**
	 * 获取：
	 */
	public String getCrtUserId() {
		return crtUserId;
	}
	/**
	 * 设置：
	 */
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdTime() {
		return updTime;
	}
	/**
	 * 设置：
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
	/**
	 * 获取：
	 */
	public String getUpdUserId() {
		return updUserId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "NoticeBaseInfo{" +
				"noticeId='" + noticeId + '\'' +
				", userRealId='" + userRealId + '\'' +
				", username='" + username + '\'' +
				", noticePath='" + noticePath + '\'' +
				", method='" + method + '\'' +
				", noticeNote='" + noticeNote + '\'' +
				", crtTime=" + crtTime +
				", crtUserId='" + crtUserId + '\'' +
				", updTime=" + updTime +
				", updUserId='" + updUserId + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
