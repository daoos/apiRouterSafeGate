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
@Table(name = "api_body_info")
public class ApiBodyInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //header表id
    @Id
    private String bodyId;
	
	    //api主键
    @Column(name = "api_id")
    private String apiId;
	
	    //param的name值
    @Column(name = "param_name")
    private String paramName;
	
	    //param校验规则
    @Column(name = "validate_reg")
    private String validateReg;

	// 正则是否为用户自定义0--是,1--不是
	@Column(name = "reg_user_defined")
	private Short regUserDefined;

	    //备注
    @Column(name = "param_note")
    private String paramNote;
	
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
	 * 设置：header表id
	 */
	public void setBodyId(String bodyId) {
		this.bodyId = bodyId;
	}
	/**
	 * 获取：header表id
	 */
	public String getBodyId() {
		return bodyId;
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
	 * 设置：param的name值
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	/**
	 * 获取：param的name值
	 */
	public String getParamName() {
		return paramName;
	}
	/**
	 * 设置：param校验规则
	 */
	public void setValidateReg(String validateReg) {
		this.validateReg = validateReg;
	}
	/**
	 * 获取：param校验规则
	 */
	public String getValidateReg() {
		return validateReg;
	}
	/**
	 * 设置：备注
	 */
	public void setParamNote(String paramNote) {
		this.paramNote = paramNote;
	}
	/**
	 * 获取：备注
	 */
	public String getParamNote() {
		return paramNote;
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

	public Short getRegUserDefined() {
		return regUserDefined;
	}

	public void setRegUserDefined(Short regUserDefined) {
		this.regUserDefined = regUserDefined;
	}

	@Override
	public String toString() {
		return "ApiBodyInfo{" +
				"bodyId='" + bodyId + '\'' +
				", apiId='" + apiId + '\'' +
				", paramName='" + paramName + '\'' +
				", validateReg='" + validateReg + '\'' +
				", regUserDefined=" + regUserDefined +
				", paramNote='" + paramNote + '\'' +
				", crtUserId='" + crtUserId + '\'' +
				", crtTime=" + crtTime +
				", updUserId='" + updUserId + '\'' +
				", updTime=" + updTime +
				'}';
	}
}
