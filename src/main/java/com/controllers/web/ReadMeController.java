package com.controllers.web;

import java.io.IOException;
import javax.annotation.PostConstruct;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.markdown4j.Markdown4jProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReadMeController {

  private static final Logger logger = Logger.getLogger(ReadMeController.class);

  @Value("classpath:/markdown/README.md")
  private Resource readMeMark;

  private String readMe;

  @PostConstruct
  private void createReadMe() {
    try {
      String readMeString = IOUtils.toString(readMeMark.getInputStream());
      readMe = new Markdown4jProcessor().process(readMeString);
    } catch (IOException ex) {
      logger.error("Error creating readme file from markdown. ERROR: " + ex.getMessage());
    }
  }

  @RequestMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
  public String getReadMe(Model model) {
    model.addAttribute("readme", readMe);
    return "faq";
  }

}
