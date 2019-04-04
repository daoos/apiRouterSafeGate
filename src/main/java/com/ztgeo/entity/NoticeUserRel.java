package com.ztgeo.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * 通知用户关系表. 表示用户的通知该转发给哪些已配置过地址的用户
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-09-14 11:58:02
 */
@Table(name = "notice_user_rel")
public class NoticeUserRel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String relId;

	@Column(name = "type_id")
	private Integer typeId;

	    //用户真实ID
    @Column(name = "user_real_id")
    private String userRealId;
	
	    //user_real_id对应的用户可转发给哪些配置过通知地址的用户
    @Column(name = "notice_id")
    private String noticeId;
	

	/**
	 * 设置：
	 */
	public void setRelId(String relId) {
		this.relId = relId;
	}
	/**
	 * 获取：
	 */
	public String getRelId() {
		return relId;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
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
	 * 设置：user_real_id对应的用户可转发给哪些配置过通知地址的用户
	 */
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	/**
	 * 获取：user_real_id对应的用户可转发给哪些配置过通知地址的用户
	 */
	public String getNoticeId() {
		return noticeId;
	}
}
