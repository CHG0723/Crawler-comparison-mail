package com.mail.utils;


import com.mail.mapper.JcSjsMapper;
import com.mail.model.JcSjsModel;
import com.mail.service.MailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Slf4j
@Service
public class Util {
    @Autowired
    MailServiceImpl mailService;

    @Value("${spring.mail.touser}")
    private String user;

    @Value("${spring.mail.filepath}")
    private String filepath;

    @Autowired
    private JcSjsMapper jcSjsMapper;

    //定时发送
    @Scheduled(cron = "0 27 11 * * ? ")
    public void sendMail() {
        //设置日期格式
        Date current = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormat.format(current);
        // 发送邮件
        mailService.sendHtmlMail(user, "公告每日统计" + time + "车红刚", getContent());
        mailService.sendHtmlMail(user,"公告发布告警"+time+"车红刚",getContent1());
        mailService.sendHtmlMail(user,"公告发布告警"+time+"车红刚",getContent2());
    }




    private String getContent() {
        // 查询总数
        JcSjsModel model1 = jcSjsMapper.countAll();
        // 查询漏发布项
        JcSjsModel model2 = jcSjsMapper.countError();
        // 查询漏发补的明细
        List<JcSjsModel> list3 = jcSjsMapper.list();

        // 生成邮件内容
        StringBuilder builder = new StringBuilder("<!DOCTYPE html>\n")
                .append("<html lang=\"en\">\n")
                .append("<head>\n")
                .append("    <meta charset=\"UTF-8\">\n")
                .append("    <title>Title</title>\n")
                .append("</head>\n")
                .append("<body>\n")
                .append("<table class=\"MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"615\" style=\"width:461.0pt;margin-left:-1.15pt;border-collapse:collapse\">\n")
                .append("    <tbody>\n")
                .append("    <tr style=\"height:24.0pt\">\n")
                .append("        <td width=\"615\" colspan=\"4\" style=\"width:461.0pt;border-top:windowtext;border-left:windowtext;border-bottom:black;border-right:black;border-style:solid;border-width:1.0pt;background:#FFCC00;padding:0cm 5.4pt 0cm 5.4pt;height:24.0pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ;;color:black \">");

        // 日期
        String dateTime = DateFormatUtils.format(new Date(), "yyyy/MM/dd");
        builder.append(dateTime).append("</span><span style=\" font-size:12.0pt ; ; ; ;;color:black \">公告统计<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n");

        builder.append("        </td>\n")
                .append("        <td width=\"0\" style=\"width:.3pt;padding:0cm 0cm 0cm 0cm;height:24.0pt\"></td>\n")
                .append("    </tr>\n")
                .append("    <tr style=\"height:31.5pt\">\n")
                .append("        <td width=\"171\" style=\"width:128.0pt;border:solid black 1.0pt;border-top:none;padding:0cm 5.4pt 0cm 5.4pt;height:31.5pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">信息源名称\n")
                .append("<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("        <td width=\"103\" style=\"width:77.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:31.5pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">公告日期\n")
                .append("<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("        <td width=\"179\" style=\"width:134.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:31.5pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">当日总量\n")
                .append("<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("        <td width=\"163\" style=\"width:122.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:31.5pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">漏发布<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("        <td width=\"0\" style=\"width:.3pt;padding:0cm 0cm 0cm 0cm;height:31.5pt\"></td>\n")
                .append("    </tr>\n");

        if (model1 != null) {
            // 设置公告统计中的巨潮
            builder.append("    <tr style=\"height:20.6pt\">\n")
                    .append("        <td width=\"171\" rowspan=\"2\" style=\"width:128.0pt;border:solid black 1.0pt;border-top:none;padding:0cm 5.4pt 0cm 5.4pt;height:20.6pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \"><span lang=\"EN-US\">巨潮<o:p></o:p></span></span></p>\n")
                    .append("        </td>\n")
                    .append("        <td width=\"103\" rowspan=\"2\" style=\"width:77.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:20.6pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ;;color:black \">")
                    .append(dateTime).append("<o:p></o:p></span></p>\n")
                    .append("        </td>\n")
                    .append("        <td width=\"179\" rowspan=\"2\" style=\"width:134.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:20.6pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ;;color:black \">")
                    .append(model1.getCount()).append("<o:p></o:p></span></p>\n")
                    .append("        </td>\n")
                    .append("        <td width=\"163\" rowspan=\"2\" style=\"width:122.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:20.6pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">　<span lang=\"EN-US\"><o:p>")
                    .append("</o:p></span></span></p>\n").append("        </td>\n")
                    .append("        <td width=\"0\" style=\"width:.3pt;padding:0cm 0cm 0cm 0cm;height:20.6pt\"></td>\n")
                    .append("    </tr>\n")
                    .append("    <tr style=\"height:20.6pt\">\n")
                    .append("        <td width=\"0\" style=\"width:.3pt;padding:0cm 0cm 0cm 0cm;height:20.6pt\"></td>\n")
                    .append("    </tr>\n");

            if (model2 != null) {
                // 设置公告统计中的上交所
                builder.append("    <tr style=\"height:20.6pt\">\n")
                        .append("        <td width=\"171\" rowspan=\"2\" style=\"width:128.0pt;border:solid black 1.0pt;border-top:none;padding:0cm 5.4pt 0cm 5.4pt;height:20.6pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \"><span lang=\"EN-US\">上交所<o:p></o:p></span></span></p>\n")
                        .append("        </td>\n")
                        .append("        <td width=\"103\" rowspan=\"2\" style=\"width:77.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:20.6pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ;;color:black \">")
                        .append(dateTime).append("<o:p></o:p></span></p>\n")
                        .append("        </td>\n")
                        .append("        <td width=\"179\" rowspan=\"2\" style=\"width:134.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:20.6pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ;;color:black \">")
                        .append(model1.getCount() - model2.getCount()).append("<o:p></o:p></span></p>\n")
                        .append("        </td>\n")
                        .append("        <td width=\"163\" rowspan=\"2\" style=\"width:122.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:20.6pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">　<span lang=\"EN-US\"><o:p>")
                        .append(model2.getCount())
                        .append("</o:p></span></span></p>\n").append("        </td>\n")
                        .append("        <td width=\"0\" style=\"width:.3pt;padding:0cm 0cm 0cm 0cm;height:20.6pt\"></td>\n")
                        .append("    </tr>\n")
                        .append("    <tr style=\"height:20.6pt\">\n")
                        .append("        <td width=\"0\" style=\"width:.3pt;padding:0cm 0cm 0cm 0cm;height:20.6pt\"></td>\n")
                        .append("    </tr>\n");
            }
        } else {
            // 出现错误的情况，一般不会出现此情况
            builder.append("    <tr style=\"height:20.6pt\">\n")
                    .append("        <td width=\"171\" rowspan=\"2\" style=\"width:128.0pt;border:solid black 1.0pt;border-top:none;padding:0cm 5.4pt 0cm 5.4pt;height:20.6pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">无<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                    .append("        </td>\n")
                    .append("        <td width=\"103\" rowspan=\"2\" style=\"width:77.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:20.6pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ;;color:black \">无<o:p></o:p></span></p>\n")
                    .append("        </td>\n")
                    .append("        <td width=\"179\" rowspan=\"2\" style=\"width:134.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:20.6pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ;;color:black \">无<o:p></o:p></span></p>\n")
                    .append("        </td>\n")
                    .append("        <td width=\"163\" rowspan=\"2\" style=\"width:122.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:20.6pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ;;color:black \">无<o:p></o:p></span></p>\n")
                    .append("        </td>\n")
                    .append("        <td width=\"0\" style=\"width:.3pt;padding:0cm 0cm 0cm 0cm;height:20.6pt\"></td>\n")
                    .append("    </tr>\n")
                    .append("    <tr style=\"height:20.6pt\">\n")
                    .append("        <td width=\"0\" style=\"width:.3pt;padding:0cm 0cm 0cm 0cm;height:20.6pt\"></td>\n")
                    .append("    </tr>\n");
        }

        builder.append("    </tbody>\n")
                .append("</table>\n")
                .append("<p class=\"MsoNormal\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ; \"><o:p>&nbsp;</o:p></span></p>\n")
                .append("<table class=\"MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"704\" style=\"width:528.0pt;margin-left:-1.15pt;border-collapse:collapse\">\n")
                .append("    <tbody>\n")
                .append("    <tr style=\"height:33.0pt\">\n")
                .append("        <td width=\"704\" colspan=\"4\" style=\"width:528.0pt;border-top:windowtext;border-left:windowtext;border-bottom:black;border-right:black;border-style:solid;border-width:1.0pt;background:#FFCC00;padding:0cm 5.4pt 0cm 5.4pt;height:33.0pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">明细统计<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("    </tr>\n")
                .append("    <tr style=\"height:33.0pt\">\n")
                .append("        <td width=\"115\" style=\"width:86.0pt;border:solid black 1.0pt;border-top:none;padding:0cm 5.4pt 0cm 5.4pt;height:33.0pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">代码\n")
                .append("<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("        <td width=\"364\" style=\"width:273.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:33.0pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">标题\n")
                .append("<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("        <td width=\"107\" style=\"width:80.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:33.0pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">错误类型\n")
                .append("<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("        <td width=\"119\" style=\"width:89.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:33.0pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">信息源\n")
                .append("<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("    </tr>\n");

        // 生成明细统计
        if (!CollectionUtils.isEmpty(list3)) {
            for (JcSjsModel jcSjsModel : list3) {
                builder.append("    <tr style=\"height:33.0pt\">\n")
                        .append("        <td width=\"115\" style=\"width:86.0pt;border:solid black 1.0pt;border-top:none;padding:0cm 5.4pt 0cm 5.4pt;height:33.0pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ;;color:black \">");
                // 明细统计中的代码
                if (StringUtils.isEmpty(jcSjsModel.getCode())) {
                    builder.append("无");
                } else {
                    builder.append(jcSjsModel.getCode());
                }
                builder.append("<o:p></o:p></span></p>\n")
                        .append("        </td>\n")
                        .append("        <td width=\"364\" style=\"width:273.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:33.0pt\">\n")
                        .append("            <p class=\"MsoNormal\" style=\"text-indent:18.0pt\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">");
                // 明细统计中的标题
                if (StringUtils.isEmpty(jcSjsModel.getTitle())) {
                    builder.append("无");
                } else {
                    builder.append(jcSjsModel.getTitle());
                }

                builder.append("<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                        .append("        </td>\n")
                        .append("        <td width=\"107\" style=\"width:80.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:33.0pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">");
                // 明细统计中的错误类型
                if (StringUtils.isEmpty(jcSjsModel.getError())) {
                    builder.append("无");
                } else {
                    builder.append(jcSjsModel.getError());
                }

                // 明细统计中的信息源
                builder.append("<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                        .append("        </td>\n")
                        .append("        <td width=\"119\" style=\"width:89.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:33.0pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">上交所<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                        .append("        </td>\n")
                        .append("    </tr>\n");
            }
        } else {
            // 出现错误的情况，一般不会出现此情况
            builder.append("    <tr style=\"height:33.0pt\">\n")
                    .append("        <td width=\"115\" style=\"width:86.0pt;border:solid black 1.0pt;border-top:none;padding:0cm 5.4pt 0cm 5.4pt;height:33.0pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ;;color:black \">无<o:p></o:p></span></p>\n")
                    .append("        </td>\n")
                    .append("        <td width=\"364\" style=\"width:273.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:33.0pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">无\n")
                    .append("<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                    .append("        </td>\n")
                    .append("        <td width=\"107\" style=\"width:80.0pt;border-top:none;border-left:none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:33.0pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">无<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                    .append("        </td>\n")
                    .append("        <td width=\"119\" style=\"width:89.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:33.0pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">无<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                    .append("        </td>\n")
                    .append("    </tr>\n");
        }

        builder.append("    </tbody>\n")
                .append("</table>\n")
                .append("\n")
                .append("\n")
                .append("</body>\n")
                .append("</html>");

        return builder.toString();
    }

    private String getContent1() {
        // 查询公告丢失情况
        List<JcSjsModel> list = jcSjsMapper.list();

        StringBuilder builder1 = new StringBuilder("<!DOCTYPE html>\n")
                .append("<html lang=\"en\">\n")
                .append("    <meta charset=\"UTF-8\">\n")
                .append("    <title>Title</title>\n")
                .append("</head>\n")
                .append("<body>\n")
                .append("<table class=\"MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"760\" style=\"width:570.0pt;margin-left:-1.15pt;border-collapse:collapse\">\n")
                .append("    <tbody>\n")
                .append("    <tr style=\"height:14.25pt\">\n")
                .append("        <td width=\"171\" style=\"width:128.0pt;border:solid windowtext 1.0pt;background:#FFCC00;padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">报告名<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("        <td width=\"103\" style=\"width:77.0pt;border:solid windowtext 1.0pt;border-left:none;background:#FFCC00;padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">来源<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("        <td width=\"179\" style=\"width:134.0pt;border:solid windowtext 1.0pt;border-left:none;background:#FFCC00;padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">巨潮发布时间<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("        <td width=\"163\" style=\"width:122.0pt;border:solid windowtext 1.0pt;border-left:none;background:#FFCC00;padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">交易所发布时间<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>")
                .append("        <td width=\"145\" style=\"width:109.0pt;border:solid windowtext 1.0pt;border-left:none;background:#FFCC00;padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">状态<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("    </tr>\n");

        if (!CollectionUtils.isEmpty(list)) {
            for (JcSjsModel jcSjsModel : list){
                builder1.append("    <tr style=\"height:76.5pt\">\n")
                        .append("        <td width=\"171\" style=\"width:128.0pt;border:solid windowtext 1.0pt;border-top:none;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">\n");
                if (StringUtils.isEmpty(jcSjsModel.getTitle())){
                    builder1.append("无");
                }else {
                    builder1.append(jcSjsModel.getTitle());
                }
                builder1
                        .append("<span lang=\"EN-US\"><o:p></o:p></span></span></p>")
                        .append("        </td>")
                        .append("        <td width=\"103\" style=\"width:77.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">上交所<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                        .append("        </td>\n")
                        .append("        <td width=\"179\" style=\"width:134.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ;;color:black \">\n");
                // 日期
                Date time1 = jcSjsModel.getNoticeTime();
                if(time1 != null) {
                    Calendar calendar = GregorianCalendar.getInstance();
                    calendar.setTime(time1);
                    calendar.add(Calendar.MINUTE, 1);

                }

                // 巨潮发布时间
                if (time1 == null) {
                    builder1.append("无");
                } else {
                    builder1.append(DateFormatUtils.format(time1, "yyyy-MM-dd HH:mm:ss"));
                }
                builder1
                        .append("<o:p></o:p></span></p>\n")
                        .append("        </td>\n")
                        .append("        <td width=\"163\" style=\"width:122.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">　<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                        .append("        </td>\n")
                        .append("        <td width=\"145\" style=\"width:109.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:red \">异常（公告丢失）<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                        .append("        </td>\n")
                        .append("    </tr>\n");

            }

        }else {
            builder1.append("<tr style=\"height:76.5pt\">\n" +
                    "        <td width=\"171\" style=\"width:128.0pt;border:solid windowtext 1.0pt;border-top:none;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n" +
                    "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">无<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n" +
                    "        </td>\n" +
                    "        <td width=\"103\" style=\"width:77.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n" +
                    "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">无<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n" +
                    "        </td>\n" +
                    "        <td width=\"179\" style=\"width:134.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n" +
                    "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ;;color:black \">无<o:p></o:p></span></p>\n" +
                    "        </td>\n" +
                    "        <td width=\"163\" style=\"width:122.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n" +
                    "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">　<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n" +
                    "        </td>\n" +
                    "        <td width=\"145\" style=\"width:109.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n" +
                    "            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:red \">无<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n" +
                    "        </td>\n" +
                    "    </tr>\n");
        }




        builder1.append("    </tbody>\n")
                .append("</table>\n")
                .append("\n")
                .append("\n")
                .append("</div>\n")
                .append("</body>\n")
                .append("</html>");
        return builder1.toString();

    }

    private String getContent2() {

        // 查询漏发补的明细
        List<JcSjsModel> list = jcSjsMapper.list();

        // 生成邮件内容
        StringBuilder builder = new StringBuilder("<!DOCTYPE html>\n")
                .append("<html lang=\"en\">\n")
                .append("<head>\n")
                .append("    <meta charset=\"UTF-8\">\n")
                .append("    <title>公告发布告警-2020-04-24</title>\n")
                .append("</head>\n")
                .append("<body>\n")
                .append("<div>\n")
                .append("<table class=\"MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"924\" style=\"width:693.0pt;margin-left:-1.15pt;border-collapse:collapse\">\n")
                .append("    <tbody>\n")
                .append("    <tr style=\"height:14.25pt\">\n")
                .append("        <td width=\"171\" style=\"width:128.0pt;border:solid windowtext 1.0pt;background:#FFCC00;padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">报告名<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("        <td width=\"103\" style=\"width:77.0pt;border:solid windowtext 1.0pt;border-left:none;background:#FFCC00;padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">来源<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("        <td width=\"179\" style=\"width:134.0pt;border:solid windowtext 1.0pt;border-left:none;background:#FFCC00;padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">巨潮发布时间<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("        <td width=\"163\" style=\"width:122.0pt;border:solid windowtext 1.0pt;border-left:none;background:#FFCC00;padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">交易所发布时间<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("        <td width=\"145\" style=\"width:109.0pt;border:solid windowtext 1.0pt;border-left:none;background:#FFCC00;padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">状态<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("        <td width=\"164\" style=\"width:123.0pt;border:solid windowtext 1.0pt;border-left:none;background:#FFCC00;padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt\">\n")
                .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">超时时间<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                .append("        </td>\n")
                .append("    </tr>\n");

        // 生成明细统计
        if (!CollectionUtils.isEmpty(list)) {
            for (JcSjsModel jcSjsModel : list) {
                builder.append("    </tr>\n")
                        .append("    <tr style=\"height:76.5pt\">\n")
                        .append("        <td width=\"171\" style=\"width:128.0pt;border:solid windowtext 1.0pt;border-top:none;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">");

                // 报告名
                if (StringUtils.isEmpty(jcSjsModel.getTitle())) {
                    builder.append("无");
                } else {
                    builder.append(jcSjsModel.getTitle());
                }

                builder.append("<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                        .append("        </td>\n")
                        .append("        <td width=\"103\" style=\"width:77.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">上交所<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                        .append("        </td>\n")
                        .append("        <td width=\"179\" style=\"width:134.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ;;color:black \">");

                // 计算上交所发布时间
                Date time1 = jcSjsModel.getNoticeTime();
                Date time2 = null;
                if(time1 != null) {
                    Calendar calendar = GregorianCalendar.getInstance();
                    calendar.setTime(time1);
                    calendar.add(Calendar.MINUTE, 1);

                    time2 = calendar.getTime();
                }

                // 巨潮发布时间
                if (time1 == null) {
                    builder.append("无");
                } else {
                    builder.append(DateFormatUtils.format(time1, "yyyy-MM-dd HH:mm:ss"));
                }

                builder.append("<o:p></o:p></span></p>\n")
                        .append("        </td>\n")
                        .append("        <td width=\"163\" style=\"width:122.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ;;color:black \">");

                // 上交所发布时间
                if (time2 == null) {
                    builder.append("无");
                } else {
                    builder.append(DateFormatUtils.format(time2, "yyyy-MM-dd HH:mm:ss"));
                }

                builder.append("<o:p></o:p></span></p>\n")
                        .append("        </td>\n")
                        .append("        <td width=\"145\" style=\"width:109.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:#00B050 \">超时发布<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                        .append("        </td>\n")
                        .append("        <td width=\"164\" style=\"width:123.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n")
                        .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ;;color:#00B050 \">1</span><span style=\" font-size:12.0pt ; ; ; ;;color:#00B050 \">分<span lang=\"EN-US\">0</span>秒<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                        .append("        </td>\n")
                        .append("    </tr>\n");
            }
        } else {
            // 出现错误的情况，一般不会出现此情况
            builder.append("    <tr style=\"height:76.5pt\">\n")
                    .append("        <td width=\"171\" style=\"width:128.0pt;border:solid windowtext 1.0pt;border-top:none;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">无<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                    .append("        </td>\n")
                    .append("        <td width=\"103\" style=\"width:77.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:black \">无<span lang=\"EN-US\"><o:p></o:p></span></span></p>\n")
                    .append("        </td>\n")
                    .append("        <td width=\"179\" style=\"width:134.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ;;color:black \">无<o:p></o:p></span></p>\n")
                    .append("        </td>\n")
                    .append("        <td width=\"163\" style=\"width:122.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span lang=\"EN-US\" style=\" font-size:12.0pt ; ; ; ;;color:black \">无<o:p></o:p></span></p>\n")
                    .append("        </td>\n")
                    .append("        <td width=\"145\" style=\"width:109.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\">无<o:p></o:p></span></span></p>\n")
                    .append("        </td>\n")
                    .append("        <td width=\"164\" style=\"width:123.0pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt\">\n")
                    .append("            <p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\"><span style=\" font-size:12.0pt ; ; ; ;;color:#00B050 \"><span lang=\"EN-US\">无<o:p></o:p></span></span></p>\n")
                    .append("        </td>\n")
                    .append("    </tr>\n");
        }

        builder.append("    </tbody>\n")
                .append("</table>\n")
                .append("</div>\n")
                .append("</body>\n")
                .append("</html>");


        return builder.toString();
    }


}

