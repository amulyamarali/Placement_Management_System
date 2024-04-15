package com.example.placement_management.controller;

import com.example.placement_management.entity.JobEntity;
import com.example.placement_management.entity.StudentEntity;
import com.example.placement_management.repository.StudentRepository;

import com.example.placement_management.repository.RecruiterRepository;
import com.example.placement_management.entity.RecruiterEntity;

import com.example.placement_management.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Controller
public class RecruiterController {
    @Autowired
    private StudentRepository repo;
    @Autowired
    private JobService jobService;
    @Autowired
    private RecruiterRepository RecruiterRepository;
    private JobEntity jobEntity;

    @RequestMapping("/recruiter_login")
    public String login(Model model) {
        return "recruiter/login_recruiter";
    }
    //    @RequestMapping("/recruiter")
//    public String showRecruiterPage(Model model) {
//        List<StudentEntity> allStudents = repo.findAll();
//        model.addAttribute("students", allStudents);
//        return "recruiter/recruiter";
//    }
    @GetMapping("/recruiter/applicants/{jobId}/")
    public String getApplicantsForJobAndRecruiter(@PathVariable Long jobId, Model model) {
        List<StudentEntity> applicants = jobService.getApplicantsByJobIdAndRecruiterId(jobId);
        model.addAttribute("applicants", applicants);
        return "recruiter/applicants";
    }

    @PostMapping("/recruiter/filter")
    public String filterStudents(@RequestParam("cgpa") double cgpa, Model model) {
        List<StudentEntity> filteredStudents = repo.findByCgpaGreaterThanEqual(cgpa);
        jobEntity.filteredStudents = filteredStudents;
        model.addAttribute("students", filteredStudents);
        return "recruiter/applicants";
    }

    @GetMapping("/recruiter/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("recruiter", new RecruiterEntity());
        return "recruiter/signup_recruiter";
    }

}
