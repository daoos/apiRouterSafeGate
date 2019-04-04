package com.ztgeo.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * 活动步骤和API关系表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
@Table(name = "wfm_activity_api_step_rel")
public class WfmActivityApiStepRel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String relId;
	
	    //步骤ID
    @Column(name = "s_id")
    private String sId;
	
	    //接口ID
    @Column(name = "api_id")
    private String apiId;
	
	    //状态(0未被访问过，1已被访问)
    @Column(name = "status")
    private Boolean status;
	

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
	/**
	 * 设置：步骤ID
	 */
	public void setSId(String sId) {
		this.sId = sId;
	}
	/**
	 * 获取：步骤ID
	 */
	public String getSId() {
		return sId;
	}
	/**
	 * 设置：接口ID
	 */
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	/**
	 * 获取：接口ID
	 */
	public String getApiId() {
		return apiId;
	}
	/**
	 * 设置：状态(0未被访问过，1已被访问)
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}
	/**
	 * 获取：状态(0未被访问过，1已被访问)
	 */
	public Boolean getStatus() {
		return status;
	}
}
