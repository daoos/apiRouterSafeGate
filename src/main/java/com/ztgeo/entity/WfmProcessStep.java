package com.ztgeo.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * 模板步骤表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
@Table(name = "wfm_process_step")
public class WfmProcessStep implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String sId;
	
	    //模板主键ID
    @Column(name = "p_id")
    private String pId;
	
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
	 * 设置：
	 */
	public void setSId(String sId) {
		this.sId = sId;
	}
	/**
	 * 获取：
	 */
	public String getSId() {
		return sId;
	}
	/**
	 * 设置：模板主键ID
	 */
	public void setPId(String pId) {
		this.pId = pId;
	}
	/**
	 * 获取：模板主键ID
	 */
	public String getPId() {
		return pId;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String getLeaderCorp() {
		return leaderCorp;
	}

	public void setLeaderCorp(String leaderCorp) {
		this.leaderCorp = leaderCorp;
	}

}
