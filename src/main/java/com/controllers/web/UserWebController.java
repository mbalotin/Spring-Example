package com.controllers.web;

import com.controllers.AuthController;
import com.models.AuthUser;
import com.services.MailerService;
import java.util.Date;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Controller
public class UserWebController {

  @Autowired
  private AuthController authentication;

  @Autowired
  private TemplateEngine templateEngine;

  @Autowired
  private MailerService mailerService;

  /**
   * http://www.thymeleaf.org/doc/articles/springmail.html
   *
   * @throws javax.mail.MessagingException
   */
  @RequestMapping(value = "sendSubscriptionToUser", method = RequestMethod.GET)
  public void sendSubscriptionToUser() throws MessagingException {

    AuthUser user = authentication.getAuthenticatedUser();

    // Prepare the evaluation context
    Context ctx = new Context(LocaleContextHolder.getLocale());
    ctx.setVariable("name", user.getUsername());
    ctx.setVariable("subscriptionDate", new Date());

    String htmlContent = templateEngine.process("sub_mail", ctx);

    String subject = "Email test";

    mailerService.sendMail(user.getEmail(), subject, htmlContent);
  }

}
