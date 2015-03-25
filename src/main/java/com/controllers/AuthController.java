package com.controllers;

import com.controllers.rest.PublisherController;
import com.models.Publisher;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

@Controller
public class AuthController implements UserDetailsService {

  @Value("${com.admin.name}")
  private String mongoAdminUser;

  @Value("${com.admin.password}")
  private String mongoAdminPass;

  @Value("${com.admin.email}")
  private String mongoAdminEmail;

  @Autowired
  private PublisherController publisherController;

  @PostConstruct
  public void createAdmin() {
    String encryptedPassword = Publisher.encryptPassword(mongoAdminPass);
    Publisher admin = publisherController.getPublisher(mongoAdminUser);

    if (admin == null) {
      admin = new Publisher();
      admin.setUsername(mongoAdminUser);
    }

    admin.setPassword(encryptedPassword);
    admin.setEmail(mongoAdminEmail);
    admin.setUserRole(1);
    publisherController.savePublisher(admin);
  }

  public Publisher getAuthenticatedUser() {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return publisherController.getPublisher(user.getUsername());
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Publisher user = publisherController.getPublisher(username);
    User userDetail = new User(user.getUsername(), user.getPassword(), true, true, true, true, getAuthorities(user.getUserRole()));
    return userDetail;
  }

  public List<GrantedAuthority> getAuthorities(Integer role) {
    final List<GrantedAuthority> authList = new ArrayList<>();
    switch (role) {
      case 1:
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        break;
      case 2:
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
    }
    return authList;
  }

}
