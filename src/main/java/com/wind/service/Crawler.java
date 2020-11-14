package com.wind.service;

import com.wind.entity.SyncData;

import java.io.IOException;
import java.util.List;

//爬取接口

public interface Crawler {

    List<SyncData> getData() throws IOException;
}
