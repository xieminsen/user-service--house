package com.xms.house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
  
  @Autowired
  private JavaMailSender mailSender;
  
  @Value("${spring.mail.username}")
  private String from;
  
  /**
   * 
   * @param subject邮件标题
   * @param content邮件内容
   * @param toEmail向谁发送
   */
  public void sendSimpleMail(String subject,String content,String toEmail){
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(content);
    mailSender.send(message);
  }
  
}
