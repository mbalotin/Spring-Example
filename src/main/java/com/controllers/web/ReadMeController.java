package com.controllers.web;

import com.controllers.ConfigurableExample;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.apache.commons.io.IOUtils;
import org.markdown4j.Markdown4jProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@DependsOn("org.springframework.context.config.internalBeanConfigurerAspect")
public class ReadMeController {

  @Value("classpath:/markdown/README.md")
  private Resource readMeMark;

  private String readMe;

  @PostConstruct
  private void createReadMe() throws IOException {
    String readMeString = IOUtils.toString(readMeMark.getInputStream());
    readMe = new Markdown4jProcessor().process(readMeString);
  }

  /**
   * MORE INFO: http://stackoverflow.com/questions/27230446/configurable-doesnt-work-for-objects-initialized-in-postconstruct-methods
   */
  @PostConstruct
  public void ConfigurableInjectionInPostConstructTest() {
    //Testing config
    ConfigurableExample conf = new ConfigurableExample();
    if (conf.publisherRepository != null) {
      System.err.println("AUTOWIRED IS NOT NULL HERE BECAUSE OF DEPENDS_ON ANNOTATION");
    }
  }

  @RequestMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
  public String getReadMe(Model model) {
    model.addAttribute("readme", readMe);
    return "faq";
  }

}
