package com.mail.service;



public interface MailService {
    //发送文本邮件
    void sendSimpleMail(String to, String subject, String content);

    //发送HTML邮件
    void sendHtmlMail(String to, String subject,String content);

    //发送附件
    void sendAttachmentsMail(String to, String subject, String content, String filePath);
}
