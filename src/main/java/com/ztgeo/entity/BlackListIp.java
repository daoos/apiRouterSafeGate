package com.ztgeo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 黑名单IP表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-09-03 14:53:45
 */
@Table(name = "black_list_ip")
public class BlackListIp implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private Integer ipId;
	
	    //IP数值
    @Column(name = "ip_content")
    private Integer ipContent;
	
	    //备注
    @Column(name = "ip_note")
    private String ipNote;
	
	    //
    @Column(name = "crt_user_id")
    private String crtUserId;
	
	    //
    @Column(name = "crt_time")
    private Date crtTime;
	
	    //
    @Column(name = "upd_user_id")
    private String updUserId;
	
	    //
    @Column(name = "upd_time")
    private Date updTime;
	

	/**
	 * 设置：
	 */
	public void setIpId(Integer ipId) {
		this.ipId = ipId;
	}
	/**
	 * 获取：
	 */
	public Integer getIpId() {
		return ipId;
	}
	/**
	 * 设置：IP数值
	 */
	public void setIpContent(Integer ipContent) {
		this.ipContent = ipContent;
	}
	/**
	 * 获取：IP数值
	 */
	public Integer getIpContent() {
		return ipContent;
	}
	/**
	 * 设置：备注
	 */
	public void setIpNote(String ipNote) {
		this.ipNote = ipNote;
	}
	/**
	 * 获取：备注
	 */
	public String getIpNote() {
		return ipNote;
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
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
	/**
	 * 获取：
	 */
	public String getUpdUserId() {
		return updUserId;
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
