package com.wind.mapper;

import com.wind.entity.SyncData;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface SyncDataMapper {


    // 插入数据库（数据表）

    void insertSelective(SyncData syncData);


    // 根据编号和标题查询信息（数据表）

    SyncData selectByOption(SyncData syncData);

     // 根据编号和标题查询信息（异常表）

    SyncData selectErrorByOption(SyncData syncData);


     //插入数据库（异常表）
    void insertErrorSelective(SyncData syncData);
}

