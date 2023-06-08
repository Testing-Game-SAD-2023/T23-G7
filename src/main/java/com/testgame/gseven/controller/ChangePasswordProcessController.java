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
import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.model.service.FindInfoService;
import com.testgame.gseven.model.service.StudentService;
import com.testgame.gseven.utility.exceptions.PasswordTokenNotFoundException;

@Controller
@RequestMapping("/changePasswordProcess")
public class ChangePasswordProcessController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private FindInfoService findInfoService;
	
	@GetMapping("/{pswtoken}")
	public String getChangePasswordProcess(@PathVariable("pswtoken") String pswtoken) {
		
		if(pswtoken.isEmpty())
			return "redirect:/login"; 
		
		return "/changePasswordProcess";
	
	}
	
	@PostMapping("/{pswtoken}")
	public String changePasswordProcess(@PathVariable("pswtoken") String pswtoken, @ModelAttribute("password") String newPassword, BindingResult result, Model model) {
		
		Student student = findInfoService.getStudentByPasswordToken(pswtoken);
		
		if(!student.isEnabled())
			result.rejectValue("email", null, "Invalid Email or Email not Enabled");
		
		if(result.hasErrors()) {
			return "/changePassword";
		}
		
		try {
			studentService.changePassword(pswtoken, newPassword);
		} catch (PasswordTokenNotFoundException e) {
			e.printStackTrace();
		}
		
		return "redirect:/login";
	}
	
}
