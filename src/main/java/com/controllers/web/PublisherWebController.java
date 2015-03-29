package com.controllers.web;

import com.controllers.AuthController;
import com.controllers.ConfigurableExample;
import com.models.Publisher;
import com.services.MailerService;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Controller
public class PublisherWebController {

  @Autowired
  private AuthController authentication;

  @Autowired
  private TemplateEngine templateEngine;

  @Autowired
  private MailerService mailerService;

  @PostConstruct
  private void postConstructTests() {
    /**
     * Testing Injection in Post Construct
     * MORE INFO: http://stackoverflow.com/questions/27230446/configurable-doesnt-work-for-objects-initialized-in-postconstruct-methods
     */
    ConfigurableExample conf = new ConfigurableExample();
    if (conf.publisherRepository != null) {
      System.err.println("AUTOWIRED IS NOT NULL HERE BECAUSE OF DEPENDS_ON ANNOTATION");
    }
  }

  /**
   * http://www.thymeleaf.org/doc/articles/springmail.html
   *
   * @throws javax.mail.MessagingException
   */
  @RequestMapping(value = "sendSubscriptionToPublisher", method = RequestMethod.GET)
  public void sendSubscriptionToPublisher() throws MessagingException {

    Publisher user = authentication.getAuthenticatedUser();

    // Prepare the evaluation context
    Context ctx = new Context(LocaleContextHolder.getLocale());
    ctx.setVariable("name", user.getUsername());
    ctx.setVariable("subscriptionDate", new Date());

    String htmlContent = templateEngine.process("sub_mail", ctx);

    String subject = "Email test";

    mailerService.sendMail(user.getEmail(), subject, htmlContent);
  }

}
