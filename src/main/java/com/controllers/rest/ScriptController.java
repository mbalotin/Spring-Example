package com.controllers.rest;

import com.controllers.AuthController;
import com.daos.ScriptRepository;
import com.models.Publisher;
import com.models.Script;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Transactional(propagation = Propagation.REQUIRED)
@RequestMapping("/scripts")
@CacheConfig(cacheNames = "scripts")
public class ScriptController {

  static final Logger logger = Logger.getLogger(ScriptController.class);

  @Value("classpath:/examples/scriptExample.json")
  private Resource scriptExample;

  @Autowired
  private AuthController authentication;

  @Autowired
  private ScriptRepository scriptRepository;

  @RequestMapping()
  public ModelAndView getScriptList() throws IOException {
    ModelAndView model = new ModelAndView("webpages/scripts");
    model.addObject("scripts", getAllScripts());
    return model;
  }

  @RequestMapping(value = "/example")
  public String getScriptExample() throws IOException {
    return IOUtils.toString(scriptExample.getInputStream());
  }

  @RequestMapping(value = "/list")
  public List<Script> getAllScripts() {
    Publisher user = authentication.getAuthenticatedUser();
    return scriptRepository.findAllByPublisher(user);
  }

  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public Script postNewScript(@RequestBody Script script) {
    Publisher owner = authentication.getAuthenticatedUser();

    if (script.getName() == null || script.getContent() == null) {
      throw new IllegalArgumentException("One or more required fields are empty. {name | content }");
    }

    script.setPublisher(owner);
    scriptRepository.save(script);

    return script;
  }

}
