package com.reeverse.gseven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping("/dashboard")
//    @PreAuthorize("hasAuthority('STUDENTE')")
    public String HomeAccount() {
        return "dashboard";
    }
}
