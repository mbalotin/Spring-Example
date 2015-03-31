package com.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Role implements Serializable {

  private static final long serialVersionUID = 1470729557539182377L;

  @Id
  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String rolename;

  @ManyToMany(mappedBy = "roles")
  private Collection<AuthUser> users;

  public Role(String role) {
    rolename = role;
  }

}
