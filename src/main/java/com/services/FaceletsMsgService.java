package com.services;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.stereotype.Service;

@Service
public class FaceletsMsgService {

  public void addError(String mensagem, boolean eventProcessedOK) {
    if (FacesContext.getCurrentInstance() != null) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", mensagem));
    }
  }

  public void addWarn(String mensagem, boolean eventProcessedOK) {
    if (FacesContext.getCurrentInstance() != null) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention!", mensagem));
    }
  }

  public void addInfo(String mensagem, boolean eventProcessedOK) {
    if (FacesContext.getCurrentInstance() != null) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", mensagem));
    }
  }

}
