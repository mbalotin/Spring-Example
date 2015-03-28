package com.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * This class facilitates writing of redirects, removing need for full paths everywhere.
 */
@Configuration
public class ThymeleafConfig {

  @Bean
  public ServletContextTemplateResolver webpagesResolver() {
    ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
    templateResolver.setTemplateMode("HTML5");
    templateResolver.setPrefix("/webpages/");
    templateResolver.setSuffix(".html");
    return templateResolver;
  }

  @Bean
  public ServletContextTemplateResolver templateResolver() {
    ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
    templateResolver.setTemplateMode("HTML5");
    templateResolver.setPrefix("/templates/");
    templateResolver.setSuffix(".html");
    return templateResolver;
  }
}
