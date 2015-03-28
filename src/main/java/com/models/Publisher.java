package com.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Entity
@EqualsAndHashCode(exclude = {"id"})
public class Publisher implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String username;

  /**
   * Removing lombok here so we can set JsonIgnore only in getter.
   */
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private String password;

  private String email;

  @JsonIgnore
  private int userRole = 2;

  public static String encryptPassword(final String password) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncoder.encode(password);
    return encodedPassword;
  }

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  @JsonProperty
  public void setPassword(String password) {
    this.password = password;
  }

}
