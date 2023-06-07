package com.testgame.gseven.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {
    @GetMapping("/dashboard")
    public String HomeAccount() {
        return "dashboard";
    }
}
