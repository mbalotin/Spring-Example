package com.controllers;

import com.daos.PublisherRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/beans/factory/annotation/Configurable.html
 *
 * @Configurable can be used to inject(@Autowired) in a class that isn't instantiate by spring (new Class).
 */
@Configurable
public class ConfigurableExample {

  @Autowired
  public PublisherRepository publisherRepository;

  /**
   * THIS DOES NOT WORK!!! The Java EE bean annotations @PostConstruct can only be applied to container-managed beans (ex: beans annotated with spring)
   */
  @PostConstruct
  public void initialize() {

  }

}
