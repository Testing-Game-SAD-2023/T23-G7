package com.testgame.gseven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.testgame.gseven.model.service.ConfirmationService;
import com.testgame.gseven.utility.exceptions.ConfirmationTokenNotFoundException;

@Controller
public class ConfirmationController {
	
	@Autowired
	private ConfirmationService confirmationService;
	
	
	@GetMapping("/confirm/token={token}")
    public String confirmEmail(@PathVariable("token") String token) {
        
		try {
			confirmationService.confirmStudentByToken(token);
		} catch (ConfirmationTokenNotFoundException e) {
			 return "wrongUser";
		}
       
		return "redirect:/login";
    }
}
