package com.reeverse.gseven.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/user/")
//    @PreAuthorize("hasAuthority('STUDENTE')")
    public String HomeAccount() {
        return "user";
    }
}
