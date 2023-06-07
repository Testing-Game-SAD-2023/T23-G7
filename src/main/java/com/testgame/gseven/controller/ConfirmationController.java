package com.testgame.gseven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.model.service.StudentService;

@Controller
public class ConfirmationController {
	
	@Autowired
	private StudentService studentService;
	
	
	@GetMapping("/confirm/token={token}")
    public String confirmEmail(@PathVariable("token") String token) {
        
		Student student = studentService.findByConfirmationToken(token);

        if (student != null) {
        	studentService.enableStudent(student);
            return "redirect:/login";
        }
        return "wrongUser";
    }
}
