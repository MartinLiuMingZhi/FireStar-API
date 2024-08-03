package com.example.service;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;

@Slf4j
@Service
public class MailService {

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    private String name = "FireStar";

    /**
     * 发送纯文本邮件
     * @param to    收件人
     * @param subject 主题
     * @param content  内容
     */
    public void sendSimpleMail(String to,String subject,String content){
        //创建邮件信息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        //设置收件人
        message.setTo(to);
        //设置邮件主题
        message.setSubject(subject);
        //设置邮件内容
        message.setText(content);
        //发送邮件
        mailSender.send(message);
        log.info("邮件发送成功");
    }

    /**
     * 发送HTML的邮件
     * @param to    收件人
     * @param subject   主题
     * @param content   内容
     * @throws MessagingException
     */
    public void sendHtmlMail(String to,String subject,String content) throws MessagingException, UnsupportedEncodingException {
        //创建邮件信息
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

        helper.setFrom(from,name);
//        helper.setFrom(from);
        //设置收件人
        helper.setTo(to);
        //设置邮件主题
        helper.setSubject(subject);
        //设置邮件内容
        helper.setText(content);
        //发送邮件
        mailSender.send(mimeMessage);
        log.info("邮件发送成功");
    }

    /**
     * 发送带附近的邮件
     * @param to    收件人
     * @param subject   主题
     * @param content   内容
     * @param filePath  附近路径
     * @throws MessagingException
     */
    public void sendAttachmentsMail(String to,String subject,String content,String filePath) throws MessagingException {
        //创建邮件信息
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(from);
        //设置收件人
        helper.setTo(to);
        //设置邮件主题
        helper.setSubject(subject);
        //设置邮件内容
        helper.setText(content,true);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = file.getFilename();
        helper.addAttachment(fileName,file);
        //发送邮件
        mailSender.send(mimeMessage);
        log.info("邮件发送成功");

    }
}
