package com.controllers;

import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Controller
public class LoginController extends WebMvcConfigurerAdapter {

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
    return new ModelAndView("login", "error", error);
  }
}
