package com.ztgeo.entity;

import java.io.Serializable;

/**
 * 用于存储Http信息
 */
public class HttpEntity implements Serializable {

    // 发送者真实ID
    private String sendUserID = "";
    // 访问的服务ID
    private String apiID = "";
    // 访问的服务名称
    private String apiName = "";
    // 访问的服务路径
    private String apiPath = "";
    // 接收者真实ID
    private String receiveUserID = "";
    // 接收者真实名称
    private String receiverUserName = "";
    // contentType
    private String contentType = "";
    // 请求方式 get post put
    private String method = "";
    // 来源url
    private String sourceUrl = "";
    // headers
    private String headers = "";
    // params，请求参数，get与post等处理方式不同，这里全部用字符串保存
    private String params = "";
    // 发送的数据
    private String sendBody = "";
    // 接收的数据
    private String receiveBody = "";
    // 时间戳
    private long currentTime;
    // year
    private int year;
    // month
    private int month;
    // day
    private int day;
    // hour
    private int hour;
    // minute
    private int minute;
    // second
    private int second;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getSendBody() {
        return sendBody;
    }

    public void setSendBody(String sendBody) {
        this.sendBody = sendBody;
    }

    public String getReceiveBody() {
        return receiveBody;
    }

    public void setReceiveBody(String receiveBody) {
        this.receiveBody = receiveBody;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public String getSendUserID() {
        return sendUserID;
    }

    public void setSendUserID(String sendUserID) {
        this.sendUserID = sendUserID;
    }

    public String getApiID() {
        return apiID;
    }

    public void setApiID(String apiID) {
        this.apiID = apiID;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getReceiveUserID() {
        return receiveUserID;
    }

    public void setReceiveUserID(String receiveUserID) {
        this.receiveUserID = receiveUserID;
    }

    public String getReceiverUserName() {
        return receiverUserName;
    }

    public void setReceiverUserName(String receiverUserName) {
        this.receiverUserName = receiverUserName;
    }
}
