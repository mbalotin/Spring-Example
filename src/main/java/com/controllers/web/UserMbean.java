package com.controllers.web;

import com.controllers.AuthController;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@ManagedBean
@Scope("view")
public class UserMbean implements Serializable {

  private static final long serialVersionUID = 1L;

  @Autowired
  private AuthController authentication;

  public String loggedUserName() {
    return authentication.getAuthenticatedUser().getUsername();
  }

}
