package com.testgame.gseven.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.model.service.StudentService;
import com.testgame.gseven.utility.exceptions.StudentAlreadyRegisteredException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
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
		
		//CHE ROBA E' ????
		Student student = new Student();
		model.addAttribute("student", student);
		return "registration";
	}
	
	@PostMapping("/registration")
	public String registration(@ModelAttribute("student") Student studentForm) {
		/*
		Student student = studentService.findUserByEmail(studentForm.getEmail());
		
		if(student != null)
			result.rejectValue("email", null, "User already registered!!");
		
		if(result.hasErrors()) {
			model.addAttribute("student", student);
			return "/registration";
		}
        */
		try {
			studentService.registerStudent(studentForm);
		} catch (StudentAlreadyRegisteredException e) {
			e.printStackTrace();
		}
		
		return "redirect:/login";
	}
}