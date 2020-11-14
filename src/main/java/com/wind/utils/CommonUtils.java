package com.wind.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



public class CommonUtils {


     //String 转 LocalDate

    public static LocalDate string2LocalDate(String date) {
        // 补齐时分秒后缀
        if(date.length() == 10){
            date = date + " 00:00:00";
        }
        // 补齐秒后缀
        if(date.length() == 16){
            date = date + ":00";
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(com.wind.utils.BaseConstants.Pattern.DATETIME));
    }

}