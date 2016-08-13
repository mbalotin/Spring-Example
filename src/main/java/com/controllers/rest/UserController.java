package com.controllers.rest;

import com.annotations.AdminOnly;
import com.models.AuthUser;
import com.repositories.UserRepository;
import com.services.AuthenticationService;
import com.services.MailerService;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
@Transactional(propagation = Propagation.REQUIRED)
@RequestMapping("rest/admin/users")
@CacheConfig(cacheNames = "users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthenticationService authentication;

  @Value("classpath:/json/userExample.json")
  private Resource userExample;

  @Autowired
  private TemplateEngine templateEngine;

  @Autowired
  private MailerService mailerService;

  @RequestMapping(value = "")
  public String getUserExample() throws IOException {
    return IOUtils.toString(userExample.getInputStream());
  }

  @AdminOnly
  @Cacheable
  @RequestMapping(value = "list")
  public Collection<AuthUser> getAllUserData() {
    return userRepository.findAll();
  }

  @AdminOnly
  @Cacheable
  @RequestMapping(value = "get/{username}", method = RequestMethod.GET)
  public AuthUser getUserByName(@PathVariable("username") String username) {
    return userRepository.findByUsername(username);
  }

  @AdminOnly
  @RequestMapping(value = "new", method = RequestMethod.POST)
  public AuthUser postNewUser(@RequestBody @Valid AuthUser user) {

    //TODO: Can I do this check better?
    if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
      throw new IllegalArgumentException("One or more fields are empty. {username | email | password}");
    }

    String encryptedPassword = AuthUser.encryptPassword(user.getPassword());
    user.setPassword(encryptedPassword);
    user.setRoles(authentication.getUserRoles());
    userRepository.save(user);

    return user;
  }

  /**
   * http://www.thymeleaf.org/doc/articles/springmail.html
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
