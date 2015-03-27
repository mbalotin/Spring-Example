package com.controllers.web;

import java.util.Properties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class HomeController {

  @RequestMapping("/")
  public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
    model.addAttribute("name", name);
    return "greetings";
  }

  @RequestMapping("/actuator")
  public String actuator() {
    return "admin/actuator";
  }

  @RequestMapping("properties")
  @ResponseBody
  Properties properties() {
    return System.getProperties();
  }
}
