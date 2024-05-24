package com.wzu.sys.service;

/**
 * 功能：服务类
 * 作者：菠萝
 * 日期：2024/3/17
 */
public interface MailService {
    //发送邮件
    boolean sendMail(String email, String text);
}
