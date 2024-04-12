package com.example.placement_management.controller;

import com.example.placement_management.entity.JobEntity;
import com.example.placement_management.entity.StudentEntity;
import com.example.placement_management.repository.JobRepository;
import com.example.placement_management.repository.StudentRepository;
import com.example.placement_management.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    //    @Autowired
//    public JobRepository repo;
    @Autowired
    private JobService jobService;
    @Autowired
    private JobRepository repo;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/upload")
    public String uploadJobs(Model model) {
        model.addAttribute("jobEntity", new JobEntity());
        return "uploadJobs";
    }

    @GetMapping("/admin_home")
    public String displayJobs(Model model) {
        List<JobEntity> jobs = jobService.listAll();
        model.addAttribute("jobs", jobs);
        return "adminJobs";
    }

    @PostMapping("/upload")
    public String uploadJobsSubmit(@ModelAttribute JobEntity jobEntity) {
        repo.save(jobEntity);
        return "redirect:/upload"; // Redirect to the upload form again or any other appropriate page
    }

    @PostMapping("/admin_home/delete/{id}")
    public String deleteJobs(@PathVariable("id") Long id) {
        jobService.delete(id);
        return "redirect:/admin_home";
    }

}