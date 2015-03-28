package com.controllers.web;

import java.util.Properties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class SystemController {

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
