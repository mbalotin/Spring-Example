package com.controllers;

import com.daos.UserRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/aop.html#aop-using-aspectj
 *
 * @Configurable can be used to inject(@Autowired) in a class that isn't instantiate by spring (new Class).
 */
@Configurable
public class ConfigurableExample {

  @Autowired
  public UserRepository userRepository;

  /**
   * THIS DOES NOT WORK!!!
   * The Java EE bean annotations @PostConstruct can only be applied to container-managed beans (ex: beans annotated with spring)
   * HOWEVER!!!
   * IF the class that is instantiating this Configurable has @DependsOn("org.springframework.context.config.internalBeanConfigurerAspect")
   * then this WILL WORK. See UseConfigurableExample.java.
   */
  @PostConstruct
  public void initialize() {
    System.err.println("THIS IS CONFIGURABLE @POSTCONSTRUCT");
  }

}
