package com.wind.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.wind.entity.SyncData;
import com.wind.mapper.SyncDataMapper;
import com.wind.service.SyncDataService;
import com.wind.utils.BaseConstants;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Service
public class SyncDataServiceImpl implements SyncDataService , SchedulingConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(SyncDataServiceImpl.class);
    @Autowired
    //抑制所有警告信息
    @SuppressWarnings("all")
    private com.wind.service.impl.InformationNetwork informationNetwork;

    @Autowired
    @SuppressWarnings("all")
    private com.wind.service.impl.StockExchange stockExchange;

    @Autowired
    @SuppressWarnings("all")
    private SyncDataMapper syncDataMapper;
   //爬取信息
    @Override
    public void getData() throws Exception {

        List<SyncData> network = informationNetwork.getData();
        //程序间隔一分钟爬取上交所信息
        Thread.sleep(60*1000);
        List<SyncData> stockEx = stockExchange.getData();
        logger.info("对比数据信息");
        logger.info("==============start==============");

        String flag = BaseConstants.Flag.NO;
        // 对比数据存储表
        for (SyncData stock : stockEx ){
            for (SyncData net : network ){
                if(stock.getCode().equals(net.getCode()) && stock.getTitile().contains(net.getTitile())){
                    stock.setShortName(net.getShortName());
                    SyncData syncData = new SyncData();
                    syncData.init(net.getCode(),stock.getTitile());
                    syncData = syncDataMapper.selectByOption(syncData);
                    if(Objects.isNull(syncData)){
                        logger.info(stock.toString());
                        syncDataMapper.insertSelective(stock);
                        flag = BaseConstants.Flag.YES;
                    }
                }
            };
            if(BaseConstants.Flag.NO.equals(flag)){
                SyncData syncData = new SyncData();
                syncData.init(stock.getCode(),stock.getTitile());
                syncData = syncDataMapper.selectErrorByOption(syncData);
                if(Objects.isNull(syncData)){
                    stock.setError(BaseConstants.ErrorCode.TIME_OUT);
                    syncDataMapper.insertErrorSelective(stock);
                }
            }
        };
        logger.info("----------end-----------");
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                //1.添加任务内容
                () -> {
                    System.out.println("执行动态定时任务: " + LocalDateTime.now().toLocalTime());
                    try {
                        getData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                },
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    // 返回执行周期(Date)
                    return new CronTrigger(BaseConstants.CRON).nextExecutionTime(triggerContext);
                }
        );
    }

}
