package com;

import com.sun.faces.config.ConfigureListener;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.context.ServletContextAware;

//Spring boot example: https://spring.io/guides/gs/spring-boot/
//JSF On Spring: https://github.com/stephanrauh/JSF-on-Spring-Boot
//No web.xml: http://www.beyondjava.net/blog/jsf-2-2-primefaces-5-spring-boot/
@EnableCaching
@EnableScheduling
@EnableWebSecurity
@EnableSpringConfigured
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(securedEnabled = true)
@SpringBootApplication
public class Application implements ServletContextAware {

  public static void main(String[] args) {
    SpringApplication.run(Application.class);
  }

  @Bean
  public ServletRegistrationBean facesServletRegistration() {
    ServletRegistrationBean registration = new ServletRegistrationBean(new FacesServlet(), "*.xhtml");
    registration.setLoadOnStartup(1);
    return registration;
  }

  @Bean
  public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
    return new ServletListenerRegistrationBean<ConfigureListener>(new ConfigureListener());
  }

  @Override
  public void setServletContext(ServletContext servletContext) {
    servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
    servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/taglibs/springsecurity.taglib.xml");
    servletContext.setInitParameter("com.ocpsoft.pretty.BASE_PACKAGES", "com.controllers.web");
    servletContext.setInitParameter("primefaces.THEME", "redmond");
  }
}
