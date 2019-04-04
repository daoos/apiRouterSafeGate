package com.ztgeo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * @author zoupeidong
 * @version 2018-08-27 19:05:19
 * @email 806316372@qq.com
 */
@Table(name = "api_query_info")
public class ApiQueryInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    //header表id
    @Id
    private String queryId;

    //api主键
    @Column(name = "api_id")
    private String apiId;

    //query的name值
    @Column(name = "query_name")
    private String queryName;

    //query校验规则
    @Column(name = "validate_reg")
    private String validateReg;

    // 正则是否为用户自定义0--是,1--不是
    @Column(name = "reg_user_defined")
    private Short regUserDefined;

    //备注
    @Column(name = "query_note")
    private String queryNote;

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
    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    /**
     * 获取：header表id
     */
    public String getQueryId() {
        return queryId;
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
     * 设置：query的name值
     */
    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    /**
     * 获取：query的name值
     */
    public String getQueryName() {
        return queryName;
    }

    /**
     * 设置：query校验规则
     */
    public void setValidateReg(String validateReg) {
        this.validateReg = validateReg;
    }

    /**
     * 获取：query校验规则
     */
    public String getValidateReg() {
        return validateReg;
    }

    /**
     * 设置：备注
     */
    public void setQueryNote(String queryNote) {
        this.queryNote = queryNote;
    }

    /**
     * 获取：备注
     */
    public String getQueryNote() {
        return queryNote;
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
        return "ApiQueryInfo{" +
                "queryId='" + queryId + '\'' +
                ", apiId='" + apiId + '\'' +
                ", queryName='" + queryName + '\'' +
                ", validateReg='" + validateReg + '\'' +
                ", regUserDefined=" + regUserDefined +
                ", queryNote='" + queryNote + '\'' +
                ", crtUserId='" + crtUserId + '\'' +
                ", crtTime=" + crtTime +
                ", updUserId='" + updUserId + '\'' +
                ", updTime=" + updTime +
                '}';
    }
}
