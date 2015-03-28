package com.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * This class facilitates writing of redirects, removing need for full paths everywhere.
 *
 * Because mails use ClassLoaderTemplateResolver, you still need to add "emails/" in front of these templates.
 * See PublisherWebController for example.
 */
@Configuration
public class ThymeleafConfig {

  @Bean
  public ClassLoaderTemplateResolver mailResolver() {
    ClassLoaderTemplateResolver mailResolver = new ClassLoaderTemplateResolver();
    mailResolver.setTemplateMode("HTML5");
    mailResolver.setPrefix("webapp/mails/");
    mailResolver.setSuffix(".html");
    mailResolver.setOrder(1);

    return mailResolver;
  }

  @Bean
  public ServletContextTemplateResolver webpagesResolver() {
    ServletContextTemplateResolver webpagesResolver = new ServletContextTemplateResolver();
    webpagesResolver.setTemplateMode("HTML5");
    webpagesResolver.setPrefix("webpages/");
    webpagesResolver.setSuffix(".html");
    webpagesResolver.setOrder(2);

    return webpagesResolver;
  }

  @Bean
  public ServletContextTemplateResolver templateResolver() {
    ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
    templateResolver.setTemplateMode("HTML5");
    templateResolver.setPrefix("templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setOrder(3);

    return templateResolver;
  }

}
