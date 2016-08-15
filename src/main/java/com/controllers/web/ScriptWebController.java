package com.controllers.web;

import com.models.AuthUser;
import com.models.Script;
import com.repositories.ScriptRepository;
import com.services.AuthenticationService;
import java.io.IOException;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/*
 * FORM validations https://spring.io/guides/gs/validating-form-input/
 */
@Controller
@RequestMapping("admin/scripts")
public class ScriptWebController {

	@Autowired
	private AuthenticationService authentication;

	@Autowired
	private ScriptRepository scriptRepository;

	@RequestMapping()
	public ModelAndView getScriptList() throws IOException {
		return new ModelAndView("admin/scripts", "scripts", getAllScripts());
	}

	public Collection<Script> getAllScripts() {
		AuthUser user = authentication.getAuthenticatedUser();
		return scriptRepository.findAllByOwner(user);
	}

	@RequestMapping(value = "testException", method = RequestMethod.GET)
	public void testException() {
		throw new NullPointerException();
	}

}
