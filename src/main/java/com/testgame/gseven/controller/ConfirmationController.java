package com.testgame.gseven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.testgame.gseven.model.service.utils.ConfirmationService;
import com.testgame.gseven.utility.exceptions.ConfirmationTokenNotFoundException;

@Controller
public class ConfirmationController {
	
	@Autowired
	private ConfirmationService confirmationService;
	
	@GetMapping("/confirm/login")
	public String redirectToLogin() {
		return "/login";
	}
	
	@GetMapping("/confirm/token={token}")
    public String confirmEmail(@PathVariable("token") String confirmationToken) {
        
		try {
			confirmationService.confirmStudentByToken(confirmationToken);
		} catch (ConfirmationTokenNotFoundException e) {
			 return "wrongUser";
		}
       
		return "redirect:/login";
    }
}
