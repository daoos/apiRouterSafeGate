package com.ztgeo.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 模板活动表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
@Table(name = "wfm_activity")
public class WfmActivity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    // 流程实例ID
    @Id
    private String aId;
	
	    //对应的模板ID
    @Column(name = "p_id")
    private String pId;
	
	    //模板名称
    @Column(name = "proc_name")
    private String procName;
	
	    //流程总时长(单位:秒)
    @Column(name = "total_limit")
    private Integer totalLimit;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //备注
    @Column(name = "note")
    private String note;
	
	    //图片json
    @Column(name = "image_json")
    private String imageJson;
	
	    //删除状态(0-未删除，1-已删除)
    @Column(name = "is_deleted")
    private Boolean isDeleted;
	
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
	

	/**
	 * 设置：步骤表ID
	 */
	public void setAId(String aId) {
		this.aId = aId;
	}
	/**
	 * 获取：步骤表ID
	 */
	public String getAId() {
		return aId;
	}
	/**
	 * 设置：对应的模板ID
	 */
	public void setPId(String pId) {
		this.pId = pId;
	}
	/**
	 * 获取：对应的模板ID
	 */
	public String getPId() {
		return pId;
	}
	/**
	 * 设置：模板名称
	 */
	public void setProcName(String procName) {
		this.procName = procName;
	}
	/**
	 * 获取：模板名称
	 */
	public String getProcName() {
		return procName;
	}
	/**
	 * 设置：流程总时长(单位:秒)
	 */
	public void setTotalLimit(Integer totalLimit) {
		this.totalLimit = totalLimit;
	}
	/**
	 * 获取：流程总时长(单位:秒)
	 */
	public Integer getTotalLimit() {
		return totalLimit;
	}
	/**
	 * 设置：状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：备注
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置：图片json
	 */
	public void setImageJson(String imageJson) {
		this.imageJson = imageJson;
	}
	/**
	 * 获取：图片json
	 */
	public String getImageJson() {
		return imageJson;
	}
	/**
	 * 设置：删除状态(0-未删除，1-已删除)
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取：删除状态(0-未删除，1-已删除)
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
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

	@Override
	public String toString() {
		return "WfmActivity{" +
				"aId=" + aId +
				", pId='" + pId + '\'' +
				", procName='" + procName + '\'' +
				", totalLimit=" + totalLimit +
				", status='" + status + '\'' +
				", note='" + note + '\'' +
				", imageJson='" + imageJson + '\'' +
				", isDeleted=" + isDeleted +
				", crtTime=" + crtTime +
				", crtUserId='" + crtUserId + '\'' +
				", updTime=" + updTime +
				", updUserId='" + updUserId + '\'' +
				'}';
	}
}
