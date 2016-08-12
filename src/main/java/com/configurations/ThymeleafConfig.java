package com.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * This class configures Thymeleaf to find the templates in the correct folders.
 * We don't need a configuration for the classpath:templates/ folder as this is the default one.
 * We also don't need to configure /resources as they are placed in a spring public folder.
 */
@Configuration
public class ThymeleafConfig {

	// For WAR
	@Bean
	public ServletContextTemplateResolver webpagesServletContextResolver() {
		ServletContextTemplateResolver webpagesResolver = new ServletContextTemplateResolver();
		webpagesResolver.setTemplateMode("HTML5");
		webpagesResolver.setPrefix("/webpages/");
		webpagesResolver.setSuffix(".html");

		return webpagesResolver;
	}

	@Bean
	public ClassLoaderTemplateResolver mailResolver() {
		ClassLoaderTemplateResolver mailResolver = new ClassLoaderTemplateResolver();
		mailResolver.setTemplateMode("HTML5");
		mailResolver.setPrefix("mails/");
		mailResolver.setSuffix(".html");

		return mailResolver;
	}

}
