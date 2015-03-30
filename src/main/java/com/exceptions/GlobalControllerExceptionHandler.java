package com.exceptions;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 This is a user friendly way of returning errors in rest requests.
 You can add new errors of rest methods here.
 */
@ControllerAdvice
class GlobalControllerExceptionHandler {

  @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class,
    MessagingException.class, UsernameNotFoundException.class})
  public void handleBadRequests(HttpServletResponse response) throws IOException {
    response.sendError(HttpStatus.BAD_REQUEST.value());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public void conflict(HttpServletResponse response, Exception ex) throws IOException {
    response.sendError(HttpStatus.CONFLICT.value(), ex.getCause().getCause().getMessage());

  }

}
