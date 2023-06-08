package com.testgame.gseven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.model.service.ChangePasswordService;
import com.testgame.gseven.model.service.FindInfoService;
import com.testgame.gseven.model.service.UtilityService;
import com.testgame.gseven.utility.exceptions.StudentNotFoundException;

@Controller
public class ChangePasswordController {
	
	@Autowired
	private FindInfoService findInfoService;
	
	@Autowired
	private ChangePasswordService changepasswordService;
	

	@GetMapping("/changePassword")
	public String changePasswordForm() {
		return "changePassword";
	}
	
	
	@PostMapping("/changePassword")
	public String changePassword(@ModelAttribute("email") String email, BindingResult result, Model model) {
		
		
		Student student = findInfoService.getStudentByEmail(email);
		
		if(student == null || student.isEnabled()==false) {
			return "wrongUser";
		}
		
		if(result.hasErrors()) {
			return "/changePassword";
		}
		
		try {
			changepasswordService.sendEmailChangePassword(email,"/changePasswordProcess/");
		} catch (StudentNotFoundException e) {
			e.printStackTrace();
		}
		
		return "redirect:/login";
	}
}
