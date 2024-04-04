package com.example.placement_management.controller;

import com.example.placement_management.entity.StudentEntity;
import com.example.placement_management.repository.StudentRepository;

import com.example.placement_management.repository.RecruiterRepository;
import com.example.placement_management.entity.RecruiterEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Controller
public class RecruiterController {
    @Autowired
    private StudentRepository repo;

    @RequestMapping("/recruiter_login")
    public String login() {
        return "recruiter/login_recruiter";
    }

    @RequestMapping("/recruiter")
    public String showRecruiterPage(Model model) {
        List<StudentEntity> allStudents = repo.findAll();
        model.addAttribute("students", allStudents);
        return "recruiter/recruiter";
    }

    @PostMapping("/recruiter/filter")
    public String filterStudents(@RequestParam("cgpa") double cgpa, Model model) {
        List<StudentEntity> filteredStudents = repo.findByCgpaGreaterThanEqual(cgpa);
        model.addAttribute("students", filteredStudents);
        return "recruiter/recruiter";
    }

    @Autowired
    private RecruiterRepository RecruiterRepository;

    @GetMapping("/recruiter/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("recruiter", new RecruiterEntity());
        return "recruiter/signup_recruiter";
    }

}
