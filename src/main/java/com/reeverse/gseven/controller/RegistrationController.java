package com.reeverse.gseven.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.reeverse.gseven.model.dto.Student;
import com.reeverse.gseven.model.service.StudentService;

@Controller
public class RegistrationController {

	@Autowired
	private StudentService studentService;
	

	@GetMapping("/registration")
	public String registrationForm(Model model) {
		
		//CHE ROBA E' ????
		Student student = new Student();
		model.addAttribute("student", student);
		return "registration";
	}
	
	@PostMapping("/registration")
	public String registration(@ModelAttribute("student") Student studentForm, BindingResult result, Model model) {
		
		Student student = studentService.findUserByEmail(studentForm.getEmail());
		
		if(student != null)
			result.rejectValue("email", null, "User already registered!!");
		
		if(result.hasErrors()) {
			model.addAttribute("student", student);
			return "/registration";
		}
        
		studentService.saveStudent(studentForm);
		
		return "redirect:/login";
	}
}