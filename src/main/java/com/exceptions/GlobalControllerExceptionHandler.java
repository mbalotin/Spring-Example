package com.exceptions;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSendException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This is a user friendly way of returning errors in rest and web requests.
 * http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
 * TODO: THIS SHOULD BE IMPROVED!
 * HOW TO ADD MODELANDVIEW RESPONSES FOR WEB?
 * HOW TO REMOVE IOEXCEPTION FROM JSON RESPONDE?
 *
 */
@ControllerAdvice
class GlobalControllerExceptionHandler {

  private final Logger LOGGER = Logger.getLogger(GlobalControllerExceptionHandler.class);

  //Rest example
  @ExceptionHandler({MessagingException.class, MailSendException.class, UsernameNotFoundException.class, NullPointerException.class})
  public void handleBadRequests(HttpServletResponse response, Exception ex) throws IOException {
    LOGGER.error(ex.getMessage(), ex);
    response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
  }

}
