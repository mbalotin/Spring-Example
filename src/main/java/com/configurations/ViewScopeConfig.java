package com.configurations;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ViewScopeConfig {

  @Bean
  public CustomScopeConfigurer viewScope() {
    CustomScopeConfigurer configurer = new CustomScopeConfigurer();
    configurer.addScope("view", new ViewScope());
    return configurer;
  }
}
