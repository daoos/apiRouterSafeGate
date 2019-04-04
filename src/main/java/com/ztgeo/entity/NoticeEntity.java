package com.ztgeo.entity;

public class NoticeEntity {
    private  String uerId;
    // contentType
    private String contentType = "";
    // 请求方式 get post put
    private String method = "";
    // url
    private String url = "";
    //noticeCode
    private  String noticeCode;
    // body
    private String body = "";
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
    //receiveId
    private String receiveId;
    //username
    private  String username;
    //name
    private  String name;
    //typedesc
    private String typedesc;
    private int status;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public String getNoticeCode() {
        return noticeCode;
    }

    public void setNoticeCode(String noticeCode) {
        this.noticeCode = noticeCode;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getUerId() {
        return uerId;
    }

    public void setUerId(String uerId) {
        this.uerId = uerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypedesc() {
        return typedesc;
    }

    public void setTypedesc(String typedesc) {
        this.typedesc = typedesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "NoticeEntity{" +
                "uerId='" + uerId + '\'' +
                ", contentType='" + contentType + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", noticeCode='" + noticeCode + '\'' +
                ", body='" + body + '\'' +
                ", currentTime=" + currentTime +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                ", receiveId='" + receiveId + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", typedesc='" + typedesc + '\'' +
                ", status=" + status +
                '}';
    }
}
