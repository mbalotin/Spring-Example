package com.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mail.html
 */
@Service
public class MailerService {

  @Autowired
  private JavaMailSender mailSender;

  @Value("${com.email}")
  private String sender;

  /*
   * Send HTML mail (simple)
   */
  public void sendMail(String recipient, String subject, String content) throws MessagingException {
    MimeMessage email = mailSender.createMimeMessage();
    buildMessage(email, recipient, subject, content);
    mailSender.send(email);
  }

  /*
   * Send HTML mail with attachment.
   */
  public void sendMailWithAttachment(String recipient, String subject, String content, String attachmentFileName, byte[] attachmentBytes, String attachmentContentType) throws MessagingException {
    MimeMessage email = mailSender.createMimeMessage();
    MimeMessageHelper message = buildMessage(email, recipient, subject, content);

    // Add the attachment
    final InputStreamSource attachmentSource = new ByteArrayResource(attachmentBytes);
    message.addAttachment(attachmentFileName, attachmentSource, attachmentContentType);

    mailSender.send(email);
  }

  public MimeMessageHelper buildMessage(MimeMessage mimeMessage, String recipient, String subject, String content) throws MessagingException {
    // Prepare message using a Spring helper
    MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    email.setFrom(sender);
    email.setTo(recipient);
    email.setSubject(subject);
    email.setText(content, true); // true = isHtml

    return email;
  }
}
