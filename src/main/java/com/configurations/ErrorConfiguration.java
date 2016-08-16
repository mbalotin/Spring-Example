package com.configurations;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
class ErrorConfiguration implements EmbeddedServletContainerCustomizer {

	/*
	 * Since no Exception is thrown for a 404 by the dispatcher
	 * this error page is needed to override the default 404 page.
	 *
	 */
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
	}

}
