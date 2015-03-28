package com.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * This class facilitates writing of redirects, removing need for full paths everywhere.
 */
@Configuration
public class ThymeleafConfig {

//  @Bean
//  public ClassLoaderTemplateResolver emailTemplateResolver() {
//    ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
//    emailTemplateResolver.setPrefix("mails/");
//    emailTemplateResolver.setSuffix(".html");
//    emailTemplateResolver.setTemplateMode("HTML5");
//    emailTemplateResolver.setCharacterEncoding("UTF-8");
//
//    return emailTemplateResolver;
//  }
  @Bean
  public ServletContextTemplateResolver webpagesResolver() {
    ServletContextTemplateResolver webpagesResolver = new ServletContextTemplateResolver();
    webpagesResolver.setTemplateMode("HTML5");
    webpagesResolver.setPrefix("/webpages/");
    webpagesResolver.setSuffix(".html");

    return webpagesResolver;
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
