package com.testgame.gseven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.model.service.FindInfoService;
import com.testgame.gseven.model.service.UtilityService;
import com.testgame.gseven.utility.exceptions.ConfirmationTokenNotFoundException;

@Controller
public class ConfirmationController {
	
	@Autowired
	private UtilityService utilityService;
	
	@Autowired
	private FindInfoService findInfoService;
	
	@GetMapping("/confirm/token={token}")
    public String confirmEmail(@PathVariable("token") String token) {
        
		Student student = null;
		try {
			student = findInfoService.getStudentByConfirmationToken(token);
		} catch (ConfirmationTokenNotFoundException ex) {
			ex.printStackTrace();
		}
        if (student != null) {
        	utilityService.enableStudent(student);
            return "redirect:/login";
        }
        return "wrongUser";
    }
}
