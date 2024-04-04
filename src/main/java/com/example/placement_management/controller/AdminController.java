package com.example.placement_management.controller;

import com.example.placement_management.entity.JobEntity;
import com.example.placement_management.entity.RecruiterEntity;
import com.example.placement_management.entity.StudentEntity;
import com.example.placement_management.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/admin_login")
    public String login() {
        return "admin/login_admin";
    }
    @RequestMapping("/upload")
    public String uploadJobs(){
        return "admin/uploadJobs";
    }
//    @RequestMapping("/save_jobs")
//    public String saveJobs(@ModelAttribute JobEntity j, Model model) {
//        jobService.uploadJob(j);
//        List<JobEntity> listJobs= jobService.listAll();
//        model.addAttribute("listJobs", listJobs);
//        return "adminJobs";
//    }

    @RequestMapping("showJobs")
    public String displayJobs(Model model, @ModelAttribute JobEntity j) {
        jobService.uploadJob(j);
        List<JobEntity> listJobs= jobService.listAll();
        model.addAttribute("listJobs", listJobs);
        return "admin/adminJobs";
    }
    @GetMapping("/editJobs/{jobId}")
    public String editJob(@PathVariable("jobId") Long jobId, Model model) {
        JobEntity job = jobService.getById(jobId);
        model.addAttribute("job", job);
        return "admin/editJobs";
    }

    @GetMapping("/admin/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("recruiter", new RecruiterEntity());
        return "admin/signup_admin";
    }


}
