package com.mail.model;

import lombok.Data;

import java.util.Date;

@Data
public class JcSjsModel {

    private Integer id;

    private String code;

    private String shortName;

    private String title;

    private Date noticeTime;

    private String error;

    private Integer count;


}
