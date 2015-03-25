package com.controllers;

import com.daos.PublisherRepository;
import com.models.Publisher;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@RequestMapping(value = "/publishers")
@CacheConfig(cacheNames = "publishers")
public class PublisherController {

  @Autowired
  private PublisherRepository publisherRepository;

  @Value("classpath:/examples/publisherExample.json")
  private Resource publisherExample;

  @Secured("ROLE_ADMIN")
  @RequestMapping(method = RequestMethod.GET)
  public String getPublisherExample() throws IOException {
    return IOUtils.toString(publisherExample.getInputStream());
  }

  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public Publisher postNewPublisher(@RequestBody Publisher publisher) {

    if (publisher.getUsername() == null || publisher.getEmail() == null || publisher.getPassword() == null) {
      throw new IllegalArgumentException("One or more fields are empty. {username | email | password}");
    }

    String encryptedPassword = Publisher.encryptPassword(publisher.getPassword());
    publisher.setPassword(encryptedPassword);
    savePublisher(publisher);

    return publisher;
  }

  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public List<Publisher> getAllPublishersData() {
    return publisherRepository.findAll();
  }

  @Cacheable
  public Publisher getPublisher(String username) {
    return publisherRepository.findByUsername(username);
  }

  public void savePublisher(Publisher publisher) {
    publisherRepository.save(publisher);
  }

}
