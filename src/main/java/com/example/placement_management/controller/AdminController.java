package com.example.placement_management.controller;

import com.example.placement_management.entity.JobEntity;
import com.example.placement_management.entity.RecruiterEntity;
import com.example.placement_management.entity.StudentEntity;
import com.example.placement_management.repository.JobRepository;
import com.example.placement_management.repository.RecruiterRepository;
import com.example.placement_management.repository.StudentRepository;
import com.example.placement_management.service.AdminService;
import com.example.placement_management.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.placement_management.service.AdminService;

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
    @Autowired
    private AdminService adminService;
    @Autowired
    private RecruiterRepository recRepo;

    @Autowired
    private StudentRepository stRepo;

    @GetMapping("/admin_login")
    public String login() {
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
        return "admin/adminJobs";
    }

//    @RequestMapping("/success/admin_home")
//    public String display(Model model) {
//        List<JobEntity> jobs = jobService.listAll("deadline"); // Sort by deadline by default
//        model.addAttribute("jobs", jobs);
//        return "admin/adminJobs";
//    }

    @PostMapping("/login/admin_home")
    public String adminLogin(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             Model model) {
        // Check if the username and password are correct
        if (adminService.authenticate(username, password)) {
            // Redirect to the admin home page if authentication is successful
            return "redirect:/admin_home"; // Change the redirect URL to the admin home page
        } else {
            // If authentication fails, display an error message to the user
            model.addAttribute("error", "Invalid username or password. Please try again.");
            return "redirect:/admin_login"; // Redirect back to the login page with an error message
        }
    }

    private List<String> getSortOptions() {
        // Define the available sort options
        return Arrays.asList("salary","deadline");
    }

    @PostMapping("/admin/upload")
    public String uploadJobsSubmit(@ModelAttribute JobEntity jobEntity) {
        repo.save(jobEntity);
        Long jobId = jobEntity.getId();
        String password = jobEntity.getRecruiter_credentials();
        String recId = jobEntity.getRecruiter_id();
        String company = jobEntity.getCompany();
        RecruiterEntity recruiter = new RecruiterEntity();
        recruiter.setId(recId);
        recruiter.setJobId(jobId);
        recruiter.setPassword(password);
        recruiter.setCompanyName(company);
//        RecruiterEntity recruiter = new RecruiterEntity(recId, password, company, jobId);
        recRepo.save(recruiter);
        return "redirect:/admin/upload"; // Redirect to the upload form again or any other appropriate page
    }

    @PostMapping("/success/admin_home/delete/{id}")
    public String deleteJobs(@PathVariable("id") Long id) {
        jobService.delete(id);
        return "redirect:/admin_home";
    }


//    newly added
    @GetMapping("/applicants/{jobId}")
    public String appliedStudents(@PathVariable Long jobId, Model model) {
        List<StudentEntity> applied = stRepo.findByJobId(jobId);
        System.out.println(applied);
        model.addAttribute("applicants", applied);
        return "admin/appliedStudents";
    }

    @GetMapping("showShortlist/{jobId}")
    public String shortList(@PathVariable("jobId") Long JobId, Model model) {
        List<StudentEntity> filtered = jobService.getShortlistForJob(JobId);
        model.addAttribute("filteredStudents", filtered);
        return "admin/shortlist";
    }
}