package com.mail.mapper;


import com.mail.model.JcSjsModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface JcSjsMapper {

    @Select("SELECT " +
            "`code`, " +
            "`titile` title, " +
            "IF(`error`='发布时间超时','漏发布','') error, " +
            "`short_name` shortName,  " +
            "notice_time noticeTime  " +
            "FROM `jc_sjs` " +
            "WHERE error='发布时间超时';")
    List<JcSjsModel> list();

    @Select("SELECT " +
            "COUNT(1) 'count' " +
            "FROM `jc_sjs`;")
    JcSjsModel countAll();

    @Select("SELECT " +
            "COUNT(1) 'count' " +
            "FROM `jc_sjs` " +
            "WHERE error='发布时间超时';")
    JcSjsModel countError();
}
