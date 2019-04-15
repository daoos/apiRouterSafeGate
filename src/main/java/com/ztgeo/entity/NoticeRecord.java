package com.ztgeo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 通知记录
 *
 * @author zoupeidong
 * @version 2018-09-17 11:52:26
 * @email 806316372@qq.com
 */
@Table(name = "notice_record")
public class NoticeRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    @Id
    private String recordId;

    //发送方公开ID
    @Column(name = "sender_id")
    private String senderId;

    //接收方真实ID
    @Column(name = "receiver_id")
    private String receiverId;

    //接收方URL
    @Column(name = "receiver_url")
    private String receiverUrl;

    //是否发送成功(0-成功，1-失败)
    @Column(name = "status")
    private int status;

    //发送时间
    @Column(name = "send_time")
    private Date sendTime;
    //重发次数
    @Column(name = "count")
    private int count;
    //发送的数据
    @Column(name = "request_data")
    private String requestData;
    //接收用户
    @Column(name = "receiver_name")
    private String receiverName;
    //接收用户名（例如bdc_dj）
    @Column(name = "receiver_usename")
    private String receiverUseName;
    //接收用户
    @Column(name = "typedesc")
    private String typedesc;

    /**
     * 设置：
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    /**
     * 获取：
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * 设置：发送方公开ID
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     * 获取：发送方公开ID
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * 设置：接收方真实ID
     */
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * 获取：接收方真实ID
     */
    public String getReceiverId() {
        return receiverId;
    }

    /**
     * 设置：接收方URL
     */
    public void setReceiverUrl(String receiverUrl) {
        this.receiverUrl = receiverUrl;
    }

    /**
     * 获取：接收方URL
     */
    public String getReceiverUrl() {
        return receiverUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 设置：发送时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取：发送时间
     */
    public Date getSendTime() {
        return sendTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverUseName() {
        return receiverUseName;
    }

    public void setReceiverUseName(String receiverUseName) {
        this.receiverUseName = receiverUseName;
    }

    public String getTypedesc() {
        return typedesc;
    }

    public void setTypedesc(String typedesc) {
        this.typedesc = typedesc;
    }
}
