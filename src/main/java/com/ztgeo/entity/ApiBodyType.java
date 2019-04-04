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
@Table(name = "api_body_type")
public class ApiBodyType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //body类型
    @Id
    private String bodyTypeId;
	
	    //类型名称
    @Column(name = "type_name")
    private String typeName;
	
	    //子类型ID
    @Column(name = "type_parent_type")
    private String typeParentType;
	
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
	

	/**
	 * 设置：body类型
	 */
	public void setBodyTypeId(String bodyTypeId) {
		this.bodyTypeId = bodyTypeId;
	}
	/**
	 * 获取：body类型
	 */
	public String getBodyTypeId() {
		return bodyTypeId;
	}
	/**
	 * 设置：类型名称
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取：类型名称
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置：子类型ID
	 */
	public void setTypeParentType(String typeParentType) {
		this.typeParentType = typeParentType;
	}
	/**
	 * 获取：子类型ID
	 */
	public String getTypeParentType() {
		return typeParentType;
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
}
