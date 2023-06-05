package com.reeverse.gseven.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.reeverse.gseven.dto.UserDto;
import com.reeverse.gseven.model.Student;
import com.reeverse.gseven.service.UserServiceImpl;

@Controller
public class RegistrationController {

	@Autowired
	private UserServiceImpl userService;
	

	@GetMapping("/registration")
	public String registrationForm(Model model) {
		
		UserDto user = new UserDto();
		model.addAttribute("user",user);
		return "registration";
	}
	
		
	
	@PostMapping("/registration")
	public String registration(@ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
		
		Student existingUser = userService.findUserByEmail(userDto.getEmail());
		
		if(existingUser != null)
			result.rejectValue("email", null, "User already registered!!");
		
		if(result.hasErrors()) {
			model.addAttribute("user",userDto);
			return "/registration";
		}
        
		userService.saveUser(userDto);

		//return "redirect:/registration?success";
		return "redirect:/login";
	}
}