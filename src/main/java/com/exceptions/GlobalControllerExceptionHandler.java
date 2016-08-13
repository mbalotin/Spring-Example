package com.exceptions;

import java.io.IOException;
import java.util.Date;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * This is a user friendly way of returning errors in rest and web requests.
 * http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
 * http://www.jayway.com/2014/10/19/spring-boot-error-responses/
 *
 */
@ControllerAdvice
class GlobalControllerExceptionHandler {

	public static final String DEFAULT_ERROR_VIEW = "error";

	//Rest examples
	@ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class,
		MessagingException.class, UsernameNotFoundException.class})
	public void handleBadRequests(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
	public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) {
		ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);

		mav.addObject("datetime", new Date());
		mav.addObject("exception", e);
		mav.addObject("url", request.getRequestURL());
		return mav;
	}
}
