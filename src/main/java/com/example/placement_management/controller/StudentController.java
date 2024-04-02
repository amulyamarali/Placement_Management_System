package com.example.placement_management.controller;

import com.example.placement_management.entity.StudentEntity;
import com.example.placement_management.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
@Controller
public class StudentController {

    @Autowired
    private StudentRepository repo;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/application")
    public String apply(@ModelAttribute StudentEntity s) {

        System.out.println(s);
        repo.save(s);
//        session.setAttribute("message","Student Application Submitted!...")
        return "redirect:/";
    }
}
