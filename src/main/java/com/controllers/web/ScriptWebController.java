package com.controllers.web;

import com.controllers.AuthController;
import com.daos.ScriptRepository;
import com.models.Publisher;
import com.models.Script;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("scripts")
public class ScriptWebController {

  @Autowired
  private AuthController authentication;

  @Autowired
  private ScriptRepository scriptRepository;

  @RequestMapping()
  public ModelAndView getScriptList() throws IOException {
    ModelAndView model = new ModelAndView("scripts");
    model.addObject("scripts", getAllScripts());
    return model;
  }

  public List<Script> getAllScripts() {
    Publisher user = authentication.getAuthenticatedUser();
    return scriptRepository.findAllByPublisher(user);
  }

}
