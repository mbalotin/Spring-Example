package com.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * This class facilitates writing of redirects, removing need for full paths everywhere.
 *
 * Because mails use ClassLoaderTemplateResolver, don't need any prefix in front of these templates.
 * See PublisherWebController for example.
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
