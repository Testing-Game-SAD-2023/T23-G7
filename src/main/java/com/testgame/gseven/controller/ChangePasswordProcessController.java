package com.testgame.gseven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.testgame.gseven.model.service.ChangePasswordService;
import com.testgame.gseven.model.service.utils.FindInfoService;
import com.testgame.gseven.utility.exceptions.PasswordTokenNotFoundException;

@Controller
@RequestMapping("/changePasswordProcess")
public class ChangePasswordProcessController {
	
	@Autowired
	private ChangePasswordService changePasswordService;
	
	@Autowired
	private FindInfoService findInfoService;
	
	@GetMapping({"", "/" })
	public String errorPasswordProcess() {
		return "redirect:/login"; 
	}
	
	@GetMapping("/{pswtoken}")
	public String getChangePasswordProcess(@PathVariable("pswtoken") String passwordToken) {
		if(passwordToken.isEmpty())
			return "redirect:/login";
		
		boolean isPasswordTokenFound = findInfoService.doesPasswordTokenExists(passwordToken);
		if(!isPasswordTokenFound) {
			return "redirect:/login";
		}
		return "changePasswordProcess";
	}
	
	@PostMapping("/{passwordToken}")
	public String changePasswordProcess(@PathVariable("passwordToken") String passwordToken, @ModelAttribute("password") String newPassword, BindingResult result) {
		
		try {
			changePasswordService.changePassword(passwordToken, newPassword);
		} catch (PasswordTokenNotFoundException e) {
			result.rejectValue("email", null, "Invalid Email");
			return "/changePassword";
		}		
		return "redirect:/login";
	}
	
}
