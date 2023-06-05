package com.reeverse.gseven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.reeverse.gseven.model.User;
import com.reeverse.gseven.service.UserServiceImpl;

@Controller
public class ResetPasswordController {
	
	@Autowired
	private UserServiceImpl userService;
	

	@GetMapping("/resetPassword")
	public String resetPasswordForm() {
		return "resetPassword";
	}
	
	
	@PostMapping("/resetPassword")
	public String resetPassword(@ModelAttribute("email") String email, BindingResult result, Model model) {
		
		User existingUser = null;
		existingUser = userService.findUserByEmail(email);
		
		
		if(existingUser == null || existingUser.isEnabled()==false) {
			return "wrongUser";
			//result.rejectValue("email", null, "Invalid Email or Email not Enabled");
		}
		
		if(result.hasErrors()) {
			return "/resetPassword";
		}
		
		//Se va tutto a buon fine salviamo la password
		userService.beginChangePassword(existingUser);
		
		
		return "redirect:?=/login";
	}

	

	
	
}
