package com.wind.entity;


import java.time.LocalDate;

public class SyncData {

    public static final String FIELD_ID = "id";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_SHORT_NAME = "shortName";
    public static final String FIELD_TITILE = "titile";
    public static final String FIELD_NOTICE_TIME = "noticeTime";



// 数据库字段
    private Long id;
    private String code;
    private String shortName;
    private String titile;
    private LocalDate noticeTime;
    private String error;


// 非数据库字段

    public void init(String code,String titile){
        this.code = code;
        this.titile = titile;
    }

    //toString方法
    @Override
    public String toString() {
        return "SyncData{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", shortName='" + shortName + '\'' +
                ", titile='" + titile + '\'' +
                ", noticeTime=" + noticeTime +
                '}';
    }


//get、set方法


    //主键
    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    //编码
    public String getCode() {

        return code;
    }

    public void setCode(String code) {

        this.code = code;
    }


    //简称
    public String getShortName() {

        return shortName;
    }

    public void setShortName(String shortName) {

        this.shortName = shortName;
    }

    //标题
    public String getTitile() {

        return titile;
    }

    public void setTitile(String titile) {

        this.titile = titile;
    }

    //公告时间
    public LocalDate getNoticeTime() {

        return noticeTime;
    }

    public void setNoticeTime(LocalDate noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getError() {

        return error;
    }

    public void setError(String error) {

        this.error = error;
    }
}
