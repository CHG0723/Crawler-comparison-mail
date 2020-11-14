package com.wind.service.impl;

import com.wind.entity.SyncData;
import com.wind.service.Crawler;
import com.wind.service.SyncDataService;
import com.wind.utils.BaseConstants;
import com.wind.utils.CommonUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;


// 爬取 上海证券交易所 信息

@Service
public class StockExchange implements Crawler{
      private static final Logger logger = LoggerFactory.getLogger(StockExchange.class);

    // 从配置文件中获取参数信息
    @Value("${service.stock-exchange-url}")
    private String stockExchangeUrl;

    @Autowired
    private SyncDataService syncDataService;

     // 爬取数据

    @SneakyThrows
    @Override
    public List<SyncData> getData(){
        logger.info("爬取 上海证券交易所 信息");
        logger.info("---------start-----------");
        // 存储数据
        List<SyncData> list = new ArrayList<>();
        Document doc = Jsoup.connect(stockExchangeUrl)
                    .ignoreContentType(true)
                    .userAgent(BaseConstants.Header.USER_AGENT)
                    .timeout(5000).get();
        Elements elements = doc.getElementsByTag("dd");
        SyncData syncData = null;
        for (Element w : elements) {
            syncData = new SyncData();
            // 编码
            String secCode = w.attr("data-seecode");
            syncData.setCode(secCode);
            // 公告时间
            LocalDate noticeTime = CommonUtils.string2LocalDate(w.select("span").text());
            syncData.setNoticeTime(noticeTime);
            // 标题
            String titile = w.select("a").attr("title");
            syncData.setTitile(titile);
            list.add(syncData);
        }
        logger.info("-------------end---------------");
        return list;
    }

}
