package com.tianll.blog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;


/**
 * @author tianll
 * @date 2024/3/23
 * 邮件工具类: 发送邮件
 */
@Component
public class MailUtils {

    private Logger logger = LoggerFactory.getLogger(MailUtils.class);

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    /**
     * 发件人邮箱
     */
    @Value(value = "${spring.mail.username}")
    private String mailFrom ;

    /**
     * 发送邮件
     */
    public void sendMail(String to, String subject, String content){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailFrom);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        try {
            javaMailSender.send(simpleMailMessage);
            logger.info("发送邮件成功");
        } catch (MailException e) {
            logger.error("发送邮件失败", e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
