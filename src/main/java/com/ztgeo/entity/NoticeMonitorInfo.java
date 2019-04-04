package com.ztgeo.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 通知监控表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-09-14 11:58:02
 */
@Table(name = "notice_monitor_info")
public class NoticeMonitorInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //
    @Column(name = "sender_user_id")
    private String senderUserId;
	
	    //
    @Column(name = "last_send_time")
    private Date lastSendTime;
	
	    //发送状态，0-发送成功，1-发送失败
    @Column(name = "send_status")
    private Boolean sendStatus;
	
	    //
    @Column(name = "crt_time")
    private Date crtTime;
	
	    //
    @Column(name = "upd_time")
    private Date updTime;
	

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
	 * 设置：
	 */
	public void setSenderUserId(String senderUserId) {
		this.senderUserId = senderUserId;
	}
	/**
	 * 获取：
	 */
	public String getSenderUserId() {
		return senderUserId;
	}
	/**
	 * 设置：
	 */
	public void setLastSendTime(Date lastSendTime) {
		this.lastSendTime = lastSendTime;
	}
	/**
	 * 获取：
	 */
	public Date getLastSendTime() {
		return lastSendTime;
	}
	/**
	 * 设置：发送状态，0-发送成功，1-发送失败
	 */
	public void setSendStatus(Boolean sendStatus) {
		this.sendStatus = sendStatus;
	}
	/**
	 * 获取：发送状态，0-发送成功，1-发送失败
	 */
	public Boolean getSendStatus() {
		return sendStatus;
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
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdTime() {
		return updTime;
	}
}
