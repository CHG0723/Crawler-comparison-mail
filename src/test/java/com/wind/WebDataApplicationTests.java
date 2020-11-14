package com.wind;

import com.wind.service.SyncDataService;

import com.wind.service.impl.InformationNetwork;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

@SpringBootTest
@EnableScheduling
@MapperScan(basePackages = { "com.wind.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
class WebDataApplicationTests {
    @Autowired
    private SyncDataService syncDataService;

    @Resource
    private InformationNetwork informationNetwork;

    //网址信息
    final String sjsUrl="http://www.sse.com.cn/disclosure/listedinfo/bulletin/s_docdatesort_desc_2019openpdf.htm";

    final String jcUrl="http://www.cninfo.com.cn/new/disclosure";


    @Test
    void contextLoads() throws IOException {
        Document doc = Jsoup.connect(sjsUrl)
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
                .timeout(50000).get();
        Elements elements = doc.getElementsByTag("dd");
        int i =0;
        for (Element w : elements) {
            System.out.println("====="+(i+1)+"=====爬取数据==========");
            System.out.println(w.select("a").attr("href"));
            // 编码
            System.out.println(w.attr("data-seecode"));
            // 公告时间
            System.out.println(w.select("span").text());
            // 标题
            System.out.println(w.select("a").attr("title"));
        }
    }

    @Test
    void juchaoLoads() throws IOException {
        final int pageSize = 20;
        int page = 0;
        int count = 0;
        int total = 0;
        do {
            page++;
            count = jcCrawler(page, pageSize);
            total += count;
        } while (count > 0);
    }
        private int jcCrawler(int pageNum,int pageSize) throws IOException{
        Connection con = Jsoup.connect(jcUrl);
        //请求头设置，特别是cookie设置
        con.header("Accept", "text/html, application/xhtml+xml, */*");
        con.header("Content-Type", "application/x-www-form-urlencoded");
        con.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))");
        //解析请求结果
        con.data("column", "sse_latest");
        con.data("pageNum", String.valueOf(pageNum));
        con.data("pageSize", String.valueOf(pageSize));
        con.data("clusterFlag", "true");
        Document doc = con
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
                .timeout(50000).post();
        String body = doc.getElementsByTag("body").text();
        JSONObject jsonobject = JSONObject.fromObject(body);
        //将json格式的字符串转换成JSONObject对象
        JSONArray array = jsonobject.getJSONArray("classifiedAnnouncements");
        if (!CollectionUtils.isEmpty(array)) {
            int count = 0;
            for (int i = 0; i < array.size(); i++) {
                JSONArray arr = (JSONArray) array.get(i);
                if (!Objects.isNull(arr)) {
                    for (int j = 0; j < arr.size(); j++) {
                        JSONObject jsonObject = arr.getJSONObject(j);
                        String secCode = jsonObject.getString("secCode");
                        String announcementTitle = jsonObject.getString("secName") + jsonObject.getString("announcementTitle");
                        String[] adjunctUrls = jsonObject.getString("adjunctUrl").split("/");
                        // 编码
                        System.out.println(secCode);
                        // 公告时间
                        System.out.println(adjunctUrls[1]);
                        // 标题
                        System.out.println(announcementTitle);
                        count++;
                    }
                }
            }
            return count;
        }

   return 0;
    }

    @Test
    void common() throws Exception {
        syncDataService.getData();
    }
    @Test
    public void getData() {
        informationNetwork.getData();
    }
}
