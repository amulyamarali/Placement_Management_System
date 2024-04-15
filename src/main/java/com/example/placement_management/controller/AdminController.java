package com.example.placement_management.controller;

import com.example.placement_management.entity.JobEntity;
import com.example.placement_management.entity.RecruiterEntity;
import com.example.placement_management.entity.StudentEntity;
import com.example.placement_management.repository.JobRepository;
import com.example.placement_management.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class AdminController {

//    @Autowired
//    public JobRepository repo;
    @Autowired
    private JobService jobService;

    @Autowired
    private JobRepository repo;

    @GetMapping("/admin_login")
    public String showlogin() {
        return "admin/login_admin";
    }


    @RequestMapping("/admin/upload")
    public String uploadJobs(Model model){


        model.addAttribute("jobEntity", new JobEntity());
        return "admin/uploadJobs";
    }

    @RequestMapping("/admin_home")
    public String displayJobs(Model model) {
        List<JobEntity> jobs = jobService.listAll("deadline"); // Sort by deadline by default
        model.addAttribute("jobs", jobs);
        System.out.println(jobs);
        return "admin/adminJobs";
    }

    @GetMapping("/applicants/{jobId}")
    public String appliedStudents(@PathVariable Long jobId, Model model) {
        List<StudentEntity> applied = jobService.getApplicantsForJob(jobId);
        model.addAttribute("applicants", applied);
        return "admin/appliedStudents";
    }

    @GetMapping("showShortlist/{jobId}")
    public String shortList(@PathVariable("jobId") Long JobId, Model model) {
        List<StudentEntity> filtered = jobService.getShortlistForJob(JobId);
        model.addAttribute("filteredStudents", filtered);
        return "admin/shortlist";
    }

    private List<String> getSortOptions() {
        // Define the available sort options
        return Arrays.asList("salary","deadline");
    }

    @PostMapping("/admin/upload")
    public String uploadJobsSubmit(@ModelAttribute JobEntity jobEntity) {
        repo.save(jobEntity);
        return "redirect:/admin_home";
    }

    @PostMapping("/admin_home/delete/{id}")
    public String deleteJobs(@PathVariable("id") Long id) {
        jobService.delete(id);
        return "redirect:/admin_home";
    }



}
