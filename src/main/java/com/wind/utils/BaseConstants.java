package com.wind.utils;

public interface BaseConstants {

    // 三分钟执行一次
    String CRON = "0 0/3 * * * ?";


     //日期时间匹配格式

    interface Pattern {
        //yyyy-MM-dd

        String DATE = "yyyy-MM-dd";

        // yyyy-MM-dd HH:mm:ss

        String DATETIME = "yyyy-MM-dd HH:mm:ss";

    }

    //分页信息
    interface Page {
       //页面大小
        Integer PAGE_SIZE = 20;
    }


    //浏览器参数头信息
    interface Header {

        String ACCEPT = "text/html, application/xhtml+xml, */*";

        String CONTENT_TYPE = "application/x-www-form-urlencoded";

        String USER_AGENT = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))";

    }
   //错误信息
    interface ErrorCode {

        // 程序错误

        String ERROR = "程序错误";

         //网络异常错误

        String ERROR_NET = "网络异常错误";

        //发布时间超时

       String TIME_OUT = "发布时间超时";
    }

    //是否进行标识
    interface Flag {
        //是
        String YES = "YES";
     //否
        String NO = "NO";
    }


}

