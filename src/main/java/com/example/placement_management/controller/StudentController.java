package com.example.placement_management.controller;

import com.example.placement_management.entity.JobEntity;
import com.example.placement_management.entity.StudentEntity;
import com.example.placement_management.repository.StudentRepository;
import com.example.placement_management.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Controller
@RequestMapping("/")
public class StudentController {

    @Autowired
    private StudentRepository repo;
    @Autowired
    private JobService jobService;

    @RequestMapping("/student_login")
    public String login() { return "login";}

    @RequestMapping("/student_jobs")
    public String displayJobLists(Model model) {
        List<JobEntity> listJobs= jobService.listAll();
        model.addAttribute("listJobs", listJobs);
        return "studentJobs";
    }
    @PostMapping("/application")
    public String apply(@ModelAttribute StudentEntity s) {

        System.out.println(s);
        repo.save(s);
//        session.setAttribute("message","Student Application Submitted!...")
        return "redirect:/save_jobs";
    }

    @GetMapping("/applyJob/{jobId}")
    public String showApplyJobForm(@PathVariable("jobId") Long jobId, Model model) {
        JobEntity job = jobService.getById(jobId);
        model.addAttribute("job", job);
        model.addAttribute("student", new StudentEntity());
        return "applyJob"; // Return the name of your application HTML page
    }
}
