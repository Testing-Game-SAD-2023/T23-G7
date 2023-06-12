package com.testgame.gseven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.testgame.gseven.model.service.ChangePasswordService;
import com.testgame.gseven.utility.exceptions.StudentNotEnabledException;
import com.testgame.gseven.utility.exceptions.StudentNotFoundException;

@Controller
public class ChangePasswordController {
	
	@Autowired
	private ChangePasswordService changePasswordService;

	@GetMapping("/changePassword")
	public String changePasswordForm() {
		return "changePassword";
	}
	
	@PostMapping("/changePassword")
	public String changePassword(@ModelAttribute("email") String email) {
		
		try {
			changePasswordService.beginChangePassword(email, "localhost:8080", "/changePasswordProcess/");
		} catch (StudentNotFoundException e) {
			return "wrongUser";
		} catch (StudentNotEnabledException e) {
			return "wrongUser";
		}
		
		return "redirect:/login";
	}
}
