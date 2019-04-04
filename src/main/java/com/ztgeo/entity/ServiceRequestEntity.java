package com.ztgeo.entity;

/**
 * 纯服务消息体，与业务流程无关
 */
public class ServiceRequestEntity extends BaseRequestEntity {

    private String httpDataType; // data类型,xml/json
    private String messageType; // 消息类型，与业务相关
    private String files; // 文件流

    public String getHttpDataType() {
        return httpDataType;
    }

    public void setHttpDataType(String httpDataType) {
        this.httpDataType = httpDataType;
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
