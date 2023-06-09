package com.testgame.gseven.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.model.service.RegistrationService;
import com.testgame.gseven.utility.exceptions.StudentAlreadyRegisteredException;

@Controller
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;
	
	@GetMapping("/registration")
	public String registrationForm(Model model) {
		Student student = new Student();
		model.addAttribute("student", student);
		return "registration";
	}
	
	@PostMapping("/registration")
	public String registration(@ModelAttribute("student") Student studentForm, BindingResult result) {
		
		try {
			registrationService.registerStudent(studentForm, "localhost:8080", "/confirm/token=");
		} catch (StudentAlreadyRegisteredException e) {
			result.rejectValue("email", null, "User already registered.");
			return "registration";
		}
		
		return "redirect:/login";
	}
	
	
}