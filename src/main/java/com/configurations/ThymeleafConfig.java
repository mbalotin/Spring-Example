package com.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * This class configures Thymeleaf to find the templates in the correct classpath folders.
 * We don't need a configuration for the classpath:templates/ folder as this is the default one.
 * We also don't need to configure /resources as they are placed in a spring public folder.
 */
@Configuration
public class ThymeleafConfig {

  @Bean
  public ClassLoaderTemplateResolver webpagesResolver() {
    ClassLoaderTemplateResolver webpagesResolver = new ClassLoaderTemplateResolver();
    webpagesResolver.setTemplateMode("HTML5");
    webpagesResolver.setPrefix("webpages/");
    webpagesResolver.setSuffix(".html");
    webpagesResolver.setOrder(1);

    return webpagesResolver;
  }

  @Bean
  public ClassLoaderTemplateResolver mailResolver() {
    ClassLoaderTemplateResolver mailResolver = new ClassLoaderTemplateResolver();
    mailResolver.setTemplateMode("HTML5");
    mailResolver.setPrefix("mails/");
    mailResolver.setSuffix(".html");
    mailResolver.setOrder(2);

    return mailResolver;
  }
}
