package com.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

//http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mail.html
//@Service
public class MailerService {

  @Autowired
  private JavaMailSender mailSender;

  @Value("${com.email.from}")
  private String sender;

  public void sendMail(String recipient, String subject, String message) throws MessagingException {

    MimeMessage mail = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mail);
    helper.setTo(recipient);
    helper.setSubject(subject);
    helper.setFrom(sender);
    helper.setText(message);

    mailSender.send(mail);
  }

}
