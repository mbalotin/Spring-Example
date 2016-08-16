package com.exceptions;

import java.net.ConnectException;
import java.util.Date;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSendException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * This is a user friendly way of returning errors in rest and web requests.
 * http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
 *
 */
@ControllerAdvice
class GlobalControllerExceptionHandler {

	private final Logger LOGGER = Logger.getLogger(GlobalControllerExceptionHandler.class);

	/**
	 * Convert a predefined exception to an HTTP Status code, usually for REST error handling
	 */
	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
	@ExceptionHandler(DataIntegrityViolationException.class)
	public void conflict() {
		LOGGER.error("Request raised a DataIntegrityViolationException");
		// Nothing to do
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error sending email.")
	@ExceptionHandler({MessagingException.class, MailSendException.class, ConnectException.class})
	public void emailSendError(Exception exception) {
		LOGGER.error("Error sending email: " + exception.getMessage(), exception);
		// Nothing to do
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Bad Credentials")
	@ExceptionHandler(UsernameNotFoundException.class)
	public void userNotFound(UsernameNotFoundException exception) {
		LOGGER.error("Bad Credentials: " + exception.getMessage(), exception);
		// Nothing to do
	}

	/**
	 * Demonstrates how to take total control - setup a model, add useful
	 * information and return the "support" view name.
	 */
	@ExceptionHandler(NullPointerException.class)
	public ModelAndView handleError(HttpServletRequest req, Exception exception) throws Exception {

		// Rethrow annotated exceptions or they will be processed here instead.
		if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null) {
			throw exception;
		}

		LOGGER.error("Request: " + req.getRequestURI() + " raised " + exception);

		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL());
		mav.addObject("timestamp", new Date().toString());
		mav.addObject("status", 500);

		mav.setViewName("/error/500");
		return mav;
	}
}
