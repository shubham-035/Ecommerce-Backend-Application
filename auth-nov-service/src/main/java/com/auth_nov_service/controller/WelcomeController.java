package com.auth_nov_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/welcome")
public class WelcomeController {
    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/hi")
    public String hi(){
        return "Hi";
    }
}
