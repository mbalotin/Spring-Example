package com.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Entity
@EqualsAndHashCode(of = {"username", "email"})
@Table(uniqueConstraints = @UniqueConstraint(name = "UniqueNameAndEmailConstraint", columnNames = {"username", "email"}))
public class AuthUser implements Serializable {

  private static final long serialVersionUID = 1580575863133427801L;

  @Id
  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String username;
  private String password;
  private String email;

  @OneToMany
  private Collection<Script> scripts;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  private Set<Role> roles;

  /**
   Ignoring lombok here so we can set JsonIgnore only in getter.
   */
  @JsonIgnore
  public String getPassword() {
    return password;
  }

  @JsonProperty
  public void setPassword(String password) {
    this.password = password;
  }

  public void addRole(Role role) {
    roles = Optional.ofNullable(roles).orElse(new HashSet<Role>());
    roles.add(role);
  }

  public static String encryptPassword(String password) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncoder.encode(password);
    return encodedPassword;
  }

}
