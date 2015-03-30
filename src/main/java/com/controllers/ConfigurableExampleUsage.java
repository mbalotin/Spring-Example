package com.controllers;

import javax.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Controller;

@Controller
@DependsOn("org.springframework.context.config.internalBeanConfigurerAspect")
public class ConfigurableExampleUsage {

  @PostConstruct
  private void postConstructTests() {
    /**
     * Testing Injection in Post Construct
     * WILL ONLY WORK IF @DependsOn("org.springframework.context.config.internalBeanConfigurerAspect")
     * MORE INFO: http://stackoverflow.com/questions/27230446/configurable-doesnt-work-for-objects-initialized-in-postconstruct-methods
     */
    ConfigurableExample conf = new ConfigurableExample();
    if (conf.userRepository != null) {
      System.err.println("AUTOWIRED IS NOT NULL HERE BECAUSE OF DEPENDS_ON ANNOTATION");
    }
  }

}
