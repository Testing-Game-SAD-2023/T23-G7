package com.testgame.gseven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String getChangePasswordProcess(@PathVariable("pswtoken") String pswtoken) {
		if(pswtoken.isEmpty())
			return "redirect:/login";
		
		boolean isPasswordTokenFound = findInfoService.doesPasswordTokenExists(pswtoken);
		if(!isPasswordTokenFound) {
			return "redirect:/login";
		}
		return "/changePasswordProcess";
	}
	
	@PostMapping("/{pswtoken}")
	public String changePasswordProcess(@PathVariable("pswtoken") String pswtoken, @ModelAttribute("password") String newPassword, BindingResult result, Model model) {
		
		try {
			changePasswordService.changePassword(pswtoken, newPassword);
		} catch (PasswordTokenNotFoundException e) {
			result.rejectValue("email", null, "Invalid Email");
			return "/changePassword";
		}		
		return "redirect:/login";
	}
	
}
