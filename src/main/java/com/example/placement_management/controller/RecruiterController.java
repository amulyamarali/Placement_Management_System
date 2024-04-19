package com.example.placement_management.controller;

import com.example.placement_management.entity.JobEntity;
import com.example.placement_management.entity.StudentDetails;
import com.example.placement_management.entity.StudentEntity;
import com.example.placement_management.repository.StudentRepository;

import com.example.placement_management.repository.RecruiterRepository;
import com.example.placement_management.entity.RecruiterEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Controller
public class RecruiterController {
    @Autowired
    private StudentRepository repo;
    @Autowired
    private RecruiterRepository repo1;

    @RequestMapping("/recruiter_login")
    public String showlogin() {
        return "recruiter/login_recruiter";
    }

    @PostMapping("/recruiterLogin")
    public String login(@RequestParam("id") String id, @RequestParam("password") String password,
                        Model model) {
        Optional<RecruiterEntity> optionalRecruiter = repo1.findById(id);
        if (optionalRecruiter.isPresent()) {
            RecruiterEntity recruiter = optionalRecruiter.get();
            if (password.equals(recruiter.getPassword())) {
                String recruiterId = recruiter.getId();
                model.addAttribute("recruiterId", recruiterId);
                return "redirect:/recruiter/" + recruiterId;
            } else {
                model.addAttribute("loginError", "Invalid username or password.");
            }
        } else {
            model.addAttribute("loginError", "Invalid username or password.");
        }
        return "recruiter/login_recruiter";
    }


    @RequestMapping("/recruiter/{recruiterId}")
    public String showRecruiterPage(Model model, @PathVariable String recruiterId) {
        Optional<RecruiterEntity> recruiter= repo1.findById(recruiterId);
        RecruiterEntity recruiter1 = recruiter.get();
        Long jobId = recruiter1.getJobId();
        List<StudentEntity> allStudents = repo.findByJobId(jobId);
        model.addAttribute("students", allStudents);
        return "recruiter/recruiter";
    }

    @PostMapping("/recruiter/filter")
    public String filterStudents(@RequestParam("cgpa") double cgpa, Model model) {
        List<StudentEntity> filteredStudents = repo.findByCgpaGreaterThanEqual(cgpa);
        model.addAttribute("students", filteredStudents);

        return "recruiter/recruiter";
    }
//    @GetMapping("/recruiter/signup")
//    public String showSignupForm(Model model) {
//        model.addAttribute("recruiter", new RecruiterEntity());
//        return "recruiter/signup_recruiter";
//    }

}
