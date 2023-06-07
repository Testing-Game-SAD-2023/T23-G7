package com.testgame.gseven.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.model.service.StudentService;
import com.testgame.gseven.utility.exceptions.StudentAlreadyRegisteredException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class RegistrationController {

	@Autowired
	private StudentService studentService;
	
	@GetMapping("/logout")
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";
	}
	
	@GetMapping("/registration")
	public String registrationForm(Model model) {
		Student student = new Student();
		model.addAttribute("student", student);
		return "registration";
	}
	
	@PostMapping("/registration")
	public String registration(@ModelAttribute("student") Student studentForm) {
		try {
			studentService.registerStudent(studentForm);
		} catch (StudentAlreadyRegisteredException e) {
			e.printStackTrace();
		}
		
		return "redirect:/login";
	}
}