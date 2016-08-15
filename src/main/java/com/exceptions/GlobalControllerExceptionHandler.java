package com.exceptions;

import java.sql.SQLException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

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
//	@ExceptionHandler({MessagingException.class, MailSendException.class, UsernameNotFoundException.class, NullPointerException.class,
//		NonUniqueResultException.class, DataIntegrityViolationException.class})
//	public void handleBadRequests(HttpServletResponse response, Exception ex) throws IOException {
//		//LOGGER.error(ex.getMessage(), ex);
//		Throwable t = ex.getCause();
//		if (t instanceof ConstraintViolationException) {
//			response.sendError(HttpStatus.BAD_REQUEST.value(), t.getCause().getMessage());
//		} else {
//			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
//		}
//
//	}
	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
 /* . . . . . . . . . . . . . EXCEPTION HANDLERS . . . . . . . . . . . . . . */
 /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
	/**
	 * Convert a predefined exception to an HTTP Status code
	 */
	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
	// 409
	@ExceptionHandler(DataIntegrityViolationException.class)
	public void conflict() {
		LOGGER.error("Request raised a DataIntegrityViolationException");
		// Nothing to do
	}

	/**
	 * Convert a predefined exception to an HTTP Status code and specify the
	 * name of a specific view that will be used to display the error.
	 *
	 * @return Exception view.
	 */
	@ExceptionHandler({SQLException.class, DataAccessException.class})
	public String databaseError(Exception exception) {
		// Nothing to do. Return value 'databaseError' used as logical view name
		// of an error page, passed to view-resolver(s) in usual way.
		LOGGER.error("Request raised " + exception.getClass().getSimpleName());
		return "databaseError";
	}

	/**
	 * Demonstrates how to take total control - setup a model, add useful
	 * information and return the "support" view name. This method explicitly
	 * creates and returns
	 *
	 * @param req
	 *            Current HTTP request.
	 * @param exception
	 *            The exception thrown - always {@link SupportInfoException}.
	 * @return The model and view used by the DispatcherServlet to generate
	 *         output.
	 * @throws Exception
	 */
	@ExceptionHandler(UsernameNotFoundException.class)
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

		mav.setViewName("support");
		return mav;
	}
}
