package com.example.placement_management.controller;

import com.example.placement_management.entity.JobEntity;
import com.example.placement_management.entity.StudentEntity;
import com.example.placement_management.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class JobController {
    @Autowired
    private JobService jobService;
    public List<JobEntity> listAll() {
        return jobService.listAll();
    }

//    @PostMapping("/save_jobs")
//    public String viewHomePage(Model model) {
//        List<JobEntity> listJobs= jobService.listAll();
//        model.addAttribute("listProducts", listJobs);
//        return "jobs";
//    }
//    @PostMapping("/save_jobs")
//    public String saveJobs(@ModelAttribute JobEntity j, Model model) {
//        jobService.uploadJob(j);
//        List<JobEntity> listJobs= jobService.listAll();
//        model.addAttribute("listJobs", listJobs);
//        return "jobs";
//    }
//    @PostMapping("/student_jobs")
//    public String displayJobLists() {
//        return "studentJobs";
//    }
    @GetMapping("/uploadJob")
    public String showUploadJobForm(Model model) {
        model.addAttribute("job", new JobEntity());
        return "uploadJob";
    }

    @PostMapping("/uploadJob")
    public String uploadJob(@ModelAttribute("job") JobEntity job) {
        jobService.uploadJob(job);
        return "redirect:/";
    }

}
