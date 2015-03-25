package com.controllers.web;

import com.controllers.AuthController;
import com.daos.ScriptRepository;
import com.models.Script;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.services.FaceletsMsgService;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@ManagedBean
@Scope("view")
@URLMappings(mappings = {
  @URLMapping(id = "scriptsView", pattern = "/scripts", viewId = "/pages/public/scripts.jsf"),
  @URLMapping(id = "scriptsList", pattern = "/scripts/list", viewId = "/pages/admin/scriptsList.jsf"),
  @URLMapping(id = "scriptsEdit", pattern = "/scripts/edit", viewId = "/pages/admin/scriptsEdit.jsf")
})
public class ScriptMbean implements Serializable {

  private static final long serialVersionUID = 1L;

  @Autowired
  private FaceletsMsgService msgService;

  @Autowired
  private AuthController authentication;

  @Autowired
  private ScriptRepository scriptRepository;

  private List<Script> scripts;
  private List<Script> filteredScripts;
  private Script selectedScript;

  public String load() {
    scripts = scriptRepository.findAllByPublisher(authentication.getAuthenticatedUser());
    selectedScript = new Script();
    return "scripts/edit";
  }

  public void delete() {
    scriptRepository.delete(selectedScript);
    load();
    msgService.addInfo("Script deleted", true);
  }

  public void save() {
    selectedScript.setPublisher(authentication.getAuthenticatedUser());
    scriptRepository.save(selectedScript);
    load();
    msgService.addInfo("Script saved", true);
  }

  public String newScript() {
    this.selectedScript = new Script();
    return "pretty:script/edit";
  }

  public List<Script> getScripts() {
    return scripts;
  }

  public void setScripts(List<Script> scripts) {
    this.scripts = scripts;
  }

  public List<Script> getFilteredScripts() {
    return filteredScripts;
  }

  public void setFilteredScripts(List<Script> filteredScripts) {
    this.filteredScripts = filteredScripts;
  }

  public Script getSelectedScript() {
    return selectedScript;
  }

  public void setSelectedScript(Script selectedScript) {
    this.selectedScript = selectedScript;
  }

}
