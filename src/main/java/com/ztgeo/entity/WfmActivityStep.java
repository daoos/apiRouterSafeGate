package com.ztgeo.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * 活动步骤表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
@Table(name = "wfm_activity_step")
public class WfmActivityStep implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //活动步骤ID
    @Id
    private String sId;
	
	    //活动表主键
    @Column(name = "a_id")
    private String aId;
	
	    //步骤名称
    @Column(name = "step_name")
    private String stepName;
	
	    //步骤状态
    @Column(name = "step_status")
    private String stepStatus;

	// 步骤说明
	@Column(name = "note")
	private String note;

	// 主管部门
	@Column(name = "leader_corp")
	private String leaderCorp;

	// 负责人姓名
	@Column(name = "leader_name")
	private String leaderName;

	// 负责人电话
	@Column(name = "leader_tel")
	private String leaderTel;


	/**
	 * 设置：活动步骤ID
	 */
	public void setSId(String sId) {
		this.sId = sId;
	}
	/**
	 * 获取：活动步骤ID
	 */
	public String getSId() {
		return sId;
	}
	/**
	 * 设置：活动表主键
	 */
	public void setAId(String aId) {
		this.aId = aId;
	}
	/**
	 * 获取：活动表主键
	 */
	public String getAId() {
		return aId;
	}
	/**
	 * 设置：步骤名称
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	/**
	 * 获取：步骤名称
	 */
	public String getStepName() {
		return stepName;
	}
	/**
	 * 设置：步骤状态
	 */
	public void setStepStatus(String stepStatus) {
		this.stepStatus = stepStatus;
	}
	/**
	 * 获取：步骤状态
	 */
	public String getStepStatus() {
		return stepStatus;
	}

	public String getLeaderCorp() {
		return leaderCorp;
	}

	public void setLeaderCorp(String leaderCorp) {
		this.leaderCorp = leaderCorp;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public String getLeaderTel() {
		return leaderTel;
	}

	public void setLeaderTel(String leaderTel) {
		this.leaderTel = leaderTel;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
