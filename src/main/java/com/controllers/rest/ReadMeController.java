package com.controllers.rest;

import com.controllers.ConfigurableExample;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.apache.commons.io.IOUtils;
import org.markdown4j.Markdown4jProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
@DependsOn("org.springframework.context.config.internalBeanConfigurerAspect")
public class ReadMeController {

  @Value("classpath:/markdown/README.md")
  private Resource readMeMark;

  @Value("classpath:/markdown/header.html")
  private Resource headerStream;

  @Value("classpath:/markdown/footer.html")
  private Resource footerStream;

  private String readMe;

  @PostConstruct
  private void createReadMe() throws IOException {
    String readMeString = IOUtils.toString(readMeMark.getInputStream());
    String html = new Markdown4jProcessor().process(readMeString);
    String header = IOUtils.toString(headerStream.getInputStream());
    String footer = IOUtils.toString(footerStream.getInputStream());
    readMe = header + html + footer;
  }

  //MORE INFO: http://stackoverflow.com/questions/27230446/configurable-doesnt-work-for-objects-initialized-in-postconstruct-methods
  @PostConstruct
  public void ConfigurableInjectionInPostConstructTest() {
    //Testing config
    ConfigurableExample conf = new ConfigurableExample();
    if (conf.publisherRepository != null) {
      System.err.println("AUTOWIRED IS NOT NULL HERE BECAUSE OF DEPENDS_ON ANNOTATION");
    }
  }

  @RequestMapping(value = "/readme", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
  public String getReadMe() {
    return readMe;
  }

}
