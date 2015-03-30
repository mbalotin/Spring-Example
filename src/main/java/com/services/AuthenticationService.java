package com.services;

import com.daos.RoleRepository;
import com.daos.UserRepository;
import com.models.AuthUser;
import com.models.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
import org.springframework.stereotype.Service;

/*
 NICE TUTORIAL: http://kielczewski.eu/2014/12/spring-boot-security-application/
 */
@Service
public class AuthenticationService implements UserDetailsService {

  @Value("${com.admin.username}")
  private String adminUsername;

  @Value("${com.admin.password}")
  private String adminPassword;

  @Value("${com.admin.email}")
  private String adminEmail;

  @Value("#{'${com.admin.roles}'.split(',')}")
  private Collection<String> adminRoles;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @PostConstruct
  public void createAdmin() {
    String encryptedPassword = AuthUser.encryptPassword(adminPassword);
    AuthUser admin = Optional.ofNullable(userRepository.findByUsername(adminUsername)).orElse(new AuthUser());

    adminRoles.forEach((role) -> admin.addRole(getRoleByName(role)));

    admin.setUsername(adminUsername);
    admin.setPassword(encryptedPassword);
    admin.setEmail(adminEmail);
    userRepository.save(admin);
  }

  public AuthUser getAuthenticatedUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByUsername(username);
  }

  public AuthUser getAuthenticatedUserByName(String username) throws UsernameNotFoundException {
    return Optional.ofNullable(userRepository.findByUsername(username))
            .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username: %s was not found", username)));
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    AuthUser domainUser = getAuthenticatedUserByName(username);

    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;

    return new User(
            domainUser.getUsername(),
            domainUser.getPassword(),
            enabled,
            accountNonExpired,
            credentialsNonExpired,
            accountNonLocked,
            getAuthorities(domainUser.getRoles()));

  }

  private Role getRoleByName(String rolename) {
    return Optional.ofNullable(roleRepository.findByRolename(rolename.trim())).orElse(new Role(rolename.trim()));
  }

  private List<GrantedAuthority> getAuthorities(Collection<Role> roles) {
    final List<GrantedAuthority> authList = new ArrayList<>();
    roles.forEach((role) -> authList.add(new SimpleGrantedAuthority(role.getRolename())));
    return authList;
  }

}
