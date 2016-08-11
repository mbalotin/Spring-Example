package com.exceptions;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This is a user friendly way of returning errors in rest and web requests.
 * http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
 * http://www.jayway.com/2014/10/19/spring-boot-error-responses/
 *
 */
@ControllerAdvice
class GlobalControllerExceptionHandler {

	//Rest examples
	@ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class,
		MessagingException.class, UsernameNotFoundException.class})
	public void handleBadRequests(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	//Web example (not a working example)
	/*
   @ExceptionHandler(Exception.class)
   public ModelAndView handleError(HttpServletRequest req, Exception exception) {
   ModelAndView mav = new ModelAndView();
   mav.addObject("exception", exception);
   mav.addObject("url", req.getRequestURL());
   mav.setViewName("error");
   return mav;
   }
	 */
}
