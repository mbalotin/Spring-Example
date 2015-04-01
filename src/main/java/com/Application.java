package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// Spring boot example: https://spring.io/guides/gs/spring-boot/
@EnableCaching
@EnableScheduling
@EnableSpringConfigured
@EnableTransactionManagement
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class);
  }

}
