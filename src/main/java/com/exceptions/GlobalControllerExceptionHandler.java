package com.exceptions;

import javax.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 This is a user friendly way of returning errors in rest requests.
 You can add new errors of rest methods here.
 */
@ControllerAdvice
class GlobalControllerExceptionHandler {

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  String handleException(HttpMessageNotReadableException ex) {
    return ex.getMessage();
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  String handleException(IllegalArgumentException ex) {
    return "The json data provided is incorrect. Error: " + ex.getMessage();
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  String handleException(MessagingException ex) {
    return "Email could not be sent. Error: " + ex.getMessage();
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  String handleException(UsernameNotFoundException ex) {
    return ex.getMessage();
  }

}
