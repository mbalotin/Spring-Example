package com.controllers.rest;

import com.daos.UserRepository;
import com.models.AuthUser;
import com.services.AuthenticationService;
import java.io.IOException;
import java.util.Collection;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional(propagation = Propagation.REQUIRED)
@RequestMapping("users")
@CacheConfig(cacheNames = "users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthenticationService authentication;

  @Value("classpath:/examples/userExample.json")
  private Resource userExample;

  @Secured("ROLE_ADMIN")
  public String getUserExample() throws IOException {
    return IOUtils.toString(userExample.getInputStream());
  }

  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "list")
  public Collection<AuthUser> getAllUserData() {
    return userRepository.findAll();
  }

  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "new", method = RequestMethod.POST)
  public AuthUser postNewUser(@RequestBody AuthUser user) {

    //TODO: Can I do this check better?
    if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
      throw new IllegalArgumentException("One or more fields are empty. {username | email | password}");
    }

    String encryptedPassword = AuthUser.encryptPassword(user.getPassword());
    user.setPassword(encryptedPassword);
    user.setRoles(authentication.getUserRoles());
    userRepository.save(user);

    return user;
  }

  @Cacheable
  public AuthUser getUserByName(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username);
  }

}
