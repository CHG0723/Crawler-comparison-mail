package com.wind.service.impl;

import com.wind.entity.SyncData;
import com.wind.service.Crawler;
import com.wind.service.SyncDataService;
import com.wind.utils.CommonUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import net.sf.json.util.JSONUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.SneakyThrows;
import org.springframework.util.CollectionUtils;

import static com.wind.utils.BaseConstants.*;

//爬取巨潮网信息
@Service
public class InformationNetwork implements Crawler {
    private static final Logger logger = LoggerFactory.getLogger(InformationNetwork.class);

    // 从配置文件中获取参数信息
    @Value("${service.information-network-url:http://www.cninfo.com.cn/new/disclosure}")
    private String informationNetworkUrl;

    @Autowired
    private SyncDataService syncDataService;

    //@SneakyThrows不用处理try catch，将其抛出
    @SneakyThrows
    @Override
    public List<SyncData> getData() {

        logger.info("爬取 巨潮资讯网 信息");
        logger.info("----------start------------");
        // 存储数据
        List<SyncData> list = new ArrayList<>();
        int page = 0;
        int count;
        int total = 0;
        do {
            page++;
            count = postForData(page, list);
            total += count;
        } while (count > 0);

        logger.info("获取数据总页数={} 数据总条数={}", page, total);
        logger.info(JSONUtils.valueToString(list));
        logger.info("-----------------end---------------");
        return list;
    }

    @SneakyThrows
    private int postForData(int pageNum, List<SyncData> list) {

        Connection con = Jsoup.connect(informationNetworkUrl);
        //请求头设置
        con.header("Accept", Header.ACCEPT);
        con.header("Content-Type", Header.CONTENT_TYPE);
        //解析请求参数信息
        con.data("column", "sse_latest");
        con.data("pageNum", String.valueOf(pageNum));
        con.data("pageSize", Page.PAGE_SIZE.toString());
        con.data("clusterFlag", "true");
        Document doc = con
                .ignoreContentType(true)
                .userAgent(Header.USER_AGENT)
                .post();
        String body = doc.getElementsByTag("body").text();
        JSONObject jsonobject = JSONObject.fromObject(body);

        //将json格式字符串转换成JSONObject对象
        JSONArray array = jsonobject.getJSONArray("classifiedAnnouncements");
        SyncData syncData = null;
        if (!CollectionUtils.isEmpty(array)) {
            int count = 0;
            for (int i = 0; i < array.size(); i++) {
                JSONArray arr = (JSONArray) array.get(i);
                if (!Objects.isNull(arr)) {
                    for (int j = 0; j < arr.size(); j++) {
                        syncData = new SyncData();
                        JSONObject jsonObject = arr.getJSONObject(j);
                        String secCode = jsonObject.getString("secCode");
                        String shortName = jsonObject.getString("secName");
                        String announcementTitle = jsonObject.getString("announcementTitle");
                        String[] adjunctUrls = jsonObject.getString("adjunctUrl").split("/");
                        // 编码
                        syncData.setCode(secCode);
                        // 简称
                        syncData.setShortName(shortName);
                        // 公告时间
                        syncData.setNoticeTime(CommonUtils.string2LocalDate(adjunctUrls[1]));
                        // 标题
                        syncData.setTitile(announcementTitle);
                        list.add(syncData);
                        count++;
                    }
                }
            }
            return count;
        }

        return 0;
    }
}
