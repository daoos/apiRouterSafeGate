package com.ztgeo.entity;

/**
 * 通知请求消息体
 */
public class NoticeRequestEntity extends BaseRequestEntity {

    private String httpDataType; // data类型,xml/json
    private String noticeCode; // 消息编码
    private String messageType; // 消息类型，与业务相关
    private String files; // 文件流

    public String getHttpDataType() {
        return httpDataType;
    }

    public void setHttpDataType(String httpDataType) {
        this.httpDataType = httpDataType;
    }

    public String getNoticeCode() {
        return noticeCode;
    }

    public void setNoticeCode(String noticeCode) {
        this.noticeCode = noticeCode;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }
}
