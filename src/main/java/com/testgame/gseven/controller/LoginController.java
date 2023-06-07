package com.testgame.gseven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


	@GetMapping("/login")
    public String LoginForm() {
        return "login";
    }

}
