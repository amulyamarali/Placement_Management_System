package com.example.placement_management.controller;

import com.example.placement_management.entity.*;
import com.example.placement_management.repository.JobRepository;
import com.example.placement_management.repository.NotificationRepository;
import com.example.placement_management.repository.RecruiterRepository;
import com.example.placement_management.repository.StudentRepository;
import com.example.placement_management.service.AdminService;
import com.example.placement_management.service.JobService;
import com.example.placement_management.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.placement_management.service.AdminService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Base interface
interface Job {
    String getTier();
}

// Base decorator class
abstract class JobDecorator implements Job {
    protected JobEntity decoratedJob;

    public JobDecorator(JobEntity decoratedJob) {
        this.decoratedJob = decoratedJob;
    }

    public String getTier() {
        return decoratedJob.getTier();
    }
}

// Concrete decorator class
class TierDecorator extends JobDecorator {
    public TierDecorator(JobEntity decoratedJob) {
        super(decoratedJob);
    }

    @Override
    public String getTier() {
        if (decoratedJob.getSalary() > 50000) {
            return "tier1";
        } else {
            return "tier2";
        }
    }
}


@Controller
public class AdminController {

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

    @RequestMapping("/admin_home")
    public String displayJobs(Model model) {
        List<JobEntity> jobs = jobService.listAll("deadline");
        if(jobs.isEmpty()) {
            model.addAttribute("errorMessage", "No jobs to display");
        }
        model.addAttribute("jobs", jobs);
        return "admin/adminJobs";
    }

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

    @GetMapping("/admin/upload")
    public String uploadJobs() {
        return "/admin/uploadJobs";
    }


    @RequestMapping("/upload")
    public String uploadJobsSubmit(@ModelAttribute JobEntity jobEntity) {
        // Create a new TierDecorator object that decorates the jobEntity object
        Job decoratedJob = new TierDecorator(jobEntity);
        // Call the getTier method of the TierDecorator object to get the tier of the job
        // and set it in the jobEntity object
        jobEntity.setTier(decoratedJob.getTier());

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
        recRepo.save(recruiter);
        return "redirect:/admin_home"; // Redirect to the upload form again or any other appropriate page
    }

    @PostMapping("/success/admin_home/delete/{id}")
    public String deleteJobs(@PathVariable("id") Long id) {
        jobService.delete(id);
        return "redirect:/admin_home";
    }


    //    newly added
    @GetMapping("/applicants/{jobId}")
    public String appliedStudents(@PathVariable Long jobId, Model model) {
//        List<StudentEntity> applied = stRepo.findByJobId(jobId);
        JobEntity job = repo.getById(jobId);
        System.out.println("job" + job);
        List<StudentEntity> applicants = new ArrayList<>();
        List<StudentDetails> applied = job.getApplicants();
        System.out.println("applicants"+applied);
        for(StudentDetails student : applied) {
            int studentId = student.getId();
            StudentEntity student1 = stRepo.getById(studentId);
            applicants.add(student1);
        }
        System.out.println("applicants"+applicants);
        model.addAttribute("applicants", applicants);
        return "admin/appliedStudents";
    }

    @Autowired
    private NotificationService notificationService;
    @GetMapping("showShortlist/{jobId}")
    public String shortList(@PathVariable("jobId") Long JobId, Model model) {
        List<StudentEntity> filtered = notificationService.findShortlistedStudentsByJobId(JobId);
        model.addAttribute("filteredStudents", filtered);
        return "admin/shortlist";
    }
}