package com.controllers.web;

import com.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * NICE SPRING EXAMPLES:
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html
 * http://www.captaindebug.com/2011/07/accessing-request-parameters-using.html#.VRiyn_nF-2U
 */
@Controller
public class GreetingController {

  @Autowired
  private AuthenticationService authentication;

  @RequestMapping("greeting")
  public String greeting(Model model) {
    String name = authentication.getAuthenticatedUser().getUsername();
    model.addAttribute("name", name);
    return "greeting";
  }

}
