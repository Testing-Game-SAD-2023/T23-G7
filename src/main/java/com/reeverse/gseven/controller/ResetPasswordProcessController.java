package com.reeverse.gseven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reeverse.gseven.model.User;
import com.reeverse.gseven.service.UserServiceImpl;

@Controller
@RequestMapping("/resetPasswordProcess")
public class ResetPasswordProcessController {
	
	@Autowired
	private UserServiceImpl userService;
	
	
	@GetMapping("/{pswtoken}")
	public String getResetPasswordProcess(@PathVariable("pswtoken") String pswtoken) {
		
		if(pswtoken.isEmpty())
			return "redirect:/login"; 
		
		return "/resetPasswordProcess";
	
	}
	
	@PostMapping("/{pswtoken}")
	public String resetPasswordProcess(@PathVariable("pswtoken") String pswtoken, @ModelAttribute("password") String newPassword, BindingResult result, Model model) {
		
		User existingUser = null;
		existingUser = userService.findByPasswordToken(pswtoken);
		
		
		if(!existingUser.isEnabled())
			result.rejectValue("email", null, "Invalid Email or Email not Enabled");
		
		if(result.hasErrors()) {
			return "/resetPassword";
		}
		
		//Se va tutto a buon fine salviamo la password
		userService.updatePassword(existingUser, newPassword);
		
		
		return "redirect:/login";
	}
	
}
