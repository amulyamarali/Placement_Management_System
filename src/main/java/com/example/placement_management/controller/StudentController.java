package com.example.placement_management.controller;

import com.example.placement_management.entity.JobEntity;
import com.example.placement_management.entity.RecruiterEntity;
import com.example.placement_management.entity.StudentDetails;
import com.example.placement_management.entity.StudentEntity;
import com.example.placement_management.repository.StudentRepository;
import com.example.placement_management.repository.StudentDetailsRepository;
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

    @GetMapping("/")
    public String landing() {
        return "landing";
    }

    @Autowired
    private StudentDetailsRepository studentDetailsRepository;

    @RequestMapping("/student_login")
    public String login() {
        return "student/login_student";
    }

    @PostMapping("/student_login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
            Model model) {
        // Find the student by username
        StudentDetails student = studentDetailsRepository.findByUsername(username);

        if (student != null && password.equals(student.getPassword())) {
            // If the student exists and the password is correct, redirect to the student
            // jobs page
            return "redirect:/student_jobs";
        } else {
            // If the student doesn't exist or the password is incorrect, show an error
            // message
            model.addAttribute("loginError", "Invalid username or password.");
            return "student/login_student";
        }
    }

    @RequestMapping("/student_jobs")
    public String displayJobLists(Model model) {
        List<JobEntity> listJobs = jobService.listAll();
        model.addAttribute("listJobs", listJobs);
        return "student/studentJobs";
    }

    @PostMapping("/application/{jobId}")
    public String apply(@PathVariable("jobId") Long jobId, @ModelAttribute StudentEntity s) {
        JobEntity job = jobService.getById(jobId); // Retrieve the job
        s.setAppliedJob(job); // Set the job
        System.out.println(s);
        repo.save(s);
        // session.setAttribute("message","Student Application Submitted!...")
        return "redirect:/student_jobs";
    }

    @Autowired
    private StudentDetailsRepository r;

    @PostMapping("/student/signup")
    public String apply(@ModelAttribute StudentDetails student) {
        // Save the student to the database
        r.save(student);

        // Redirect to the student jobs page
        return "redirect:/student_jobs";
    }

    @GetMapping("/applyJob/{jobId}")
    public String showApplyJobForm(@PathVariable("jobId") Long jobId, Model model) {
        JobEntity job = jobService.getById(jobId);
        model.addAttribute("job", job);
        model.addAttribute("student", new StudentEntity());
        return "student/applyJob"; // Return the name of your application HTML page
    }

    @GetMapping("/student/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("recruiter", new RecruiterEntity());
        return "student/signup_student";
    }
}
