package com.testgame.gseven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.testgame.gseven.model.service.AuthService;

@Controller
public class LoginController {

	@Autowired
	private AuthService authService;
	
	@GetMapping("/login")
    public String LoginForm() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authService.isStudentAuthenticated(authentication)) {
        	return "redirect:/dashboard";
        }
        return "login";
    }

}
