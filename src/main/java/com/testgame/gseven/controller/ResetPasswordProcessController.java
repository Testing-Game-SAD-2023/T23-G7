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
import com.testgame.gseven.model.service.StudentService;

@Controller
@RequestMapping("/resetPasswordProcess")
public class ResetPasswordProcessController {
	
	@Autowired
	private StudentService studentService;
	
	
	@GetMapping("/{pswtoken}")
	public String getResetPasswordProcess(@PathVariable("pswtoken") String pswtoken) {
		
		if(pswtoken.isEmpty())
			return "redirect:/login"; 
		
		return "/resetPasswordProcess";
	
	}
	
	@PostMapping("/{pswtoken}")
	public String resetPasswordProcess(@PathVariable("pswtoken") String pswtoken, @ModelAttribute("password") String newPassword, BindingResult result, Model model) {
		
		 Student student = studentService.findByPasswordToken(pswtoken);
		
		
		if(!student.isEnabled())
			result.rejectValue("email", null, "Invalid Email or Email not Enabled");
		
		if(result.hasErrors()) {
			return "/resetPassword";
		}
		
		//Se va tutto a buon fine salviamo la password
		studentService.updatePassword(student, newPassword);
		
		return "redirect:/login";
	}
	
}
