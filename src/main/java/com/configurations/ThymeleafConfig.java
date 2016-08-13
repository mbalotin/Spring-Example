package com.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * This class configures Thymeleaf to find the templates in the correct folders.
 * We don't need to configure /resources as they are placed in a spring public folder.
 */
@Configuration
public class ThymeleafConfig {

  /**
   * For JAR and executable WAR
   * We don't need a configuration for the classpath:templates/ folder as this uses the default one.
   */
  @Bean
  public ClassLoaderTemplateResolver webpagesClassLoaderResolver() {
    ClassLoaderTemplateResolver webpagesResolver = new ClassLoaderTemplateResolver();
    webpagesResolver.setPrefix("webpages/");
    webpagesResolver.setSuffix(".html");

    return webpagesResolver;
  }

  @Bean
  public ClassLoaderTemplateResolver mailClassLoaderResolver() {
    ClassLoaderTemplateResolver mailResolver = new ClassLoaderTemplateResolver();
    mailResolver.setPrefix("mails/");
    mailResolver.setSuffix(".html");

    return mailResolver;
  }

  /**
   * For deployable WAR
   */
  @Bean
  public ServletContextTemplateResolver webpagesServletContextResolver() {
    ServletContextTemplateResolver webpagesResolver = new ServletContextTemplateResolver();
    webpagesResolver.setPrefix("/webpages/");
    webpagesResolver.setSuffix(".html");

    return webpagesResolver;
  }

  @Bean
  public ServletContextTemplateResolver templatesServletContextResolver() {
    ServletContextTemplateResolver webpagesResolver = new ServletContextTemplateResolver();
    webpagesResolver.setPrefix("/templates/");
    webpagesResolver.setSuffix(".html");

    return webpagesResolver;
  }

  @Bean
  public ServletContextTemplateResolver mailServletContextResolver() {
    ServletContextTemplateResolver mailResolver = new ServletContextTemplateResolver();
    mailResolver.setPrefix("/mails/");
    mailResolver.setSuffix(".html");

    return mailResolver;
  }

}
