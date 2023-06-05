package com.reeverse.gseven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.reeverse.gseven.model.Student;
import com.reeverse.gseven.service.UserServiceImpl;

@Controller
public class ConfirmationController {
	
	@Autowired
	private UserServiceImpl userService;
	
	
	@GetMapping("/confirm/token={token}")
    public String confirmEmail(@PathVariable("token") String token) {
        

		Student user = userService.findByConfirmationToken(token);

        if (user != null) {
            userService.enableUser(user);
            return "redirect:/login";
        }
        return "wrongUser";
    }
}
