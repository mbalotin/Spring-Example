package com.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Script implements Serializable {

  private static final long serialVersionUID = -354195658857743055L;

  @Id
  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;

  @ManyToOne
  @JsonIgnore
  private Publisher publisher;

  private String content;

  public Script() {
  }

  public Script(String name) {
    this.name = name;
  }

}
