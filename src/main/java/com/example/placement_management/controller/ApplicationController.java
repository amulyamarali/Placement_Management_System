package com.example.placement_management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class ApplicationController {

    @GetMapping("/login")
    public String welcome()
    {
        return "Hello";
    }

}