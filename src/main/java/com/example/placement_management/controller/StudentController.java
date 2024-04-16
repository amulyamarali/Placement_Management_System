package com.example.placement_management.controller;

import com.example.placement_management.entity.JobEntity;
import com.example.placement_management.entity.RecruiterEntity;
import com.example.placement_management.entity.StudentDetails;
import com.example.placement_management.entity.StudentEntity;
import com.example.placement_management.repository.StudentRepository;
import com.example.placement_management.repository.StudentDetailsRepository;
import com.example.placement_management.service.JobService;
import org.springframework.beans.PropertyEditorRegistrar;
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
    @Autowired
    private StudentDetailsRepository r;

    @PostMapping("/student/signup")
    public String apply(@ModelAttribute StudentDetails student) {
        r.save(student);
        int studentId = student.getId();
        return "redirect:/student_jobs/"+studentId;
    }

    @GetMapping("/student_login")
    public String Showlogin() {
        return "student/login_student";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
            Model model) {
        StudentDetails student = r.findByUsername(username);
        System.out.println(student);
        if (student == null) {
            model.addAttribute("loginError", "Invalid username or password.");
            return "student/login_student";
        }
        else if (password.equals(student.getPassword())) {
            int studentId = student.getId();
            model.addAttribute("studentId", studentId);
            return "redirect:/student_jobs/"+studentId;
        }
        else {
            model.addAttribute("loginError", "Invalid username or password.");
            return "/student/login_student";
        }
    }

    @RequestMapping("/student_jobs/{studentId}")
    public String displayJobLists(Model model, @PathVariable int studentId) {
        List<JobEntity> listJobs = jobService.listAll();
        model.addAttribute("listJobs", listJobs);
        model.addAttribute("studentId", studentId);
        return "student/studentJobs";
    }

    @RequestMapping("/applyJob/{studentId}/{jobId}")
    public String showApplyJobForm(@PathVariable("jobId") Long jobId, @PathVariable int studentId, @ModelAttribute StudentEntity student, Model model) {
        JobEntity job = jobService.getById(jobId);
        student.setId(studentId);
        student.setAppliedJobs(job); // Set the job
        repo.save(student);
        jobService.applyForJob(jobId, student);
        model.addAttribute("job", job);
        model.addAttribute("student", student);
        return "student/applyJob";
    }

//    @PostMapping("/submit_application/{studentId}")
//    public String submitApplication(@PathVariable("studentId") Long studentId, @ModelAttribute StudentEntity student) {
//        repo.save(student);
//        return "redirect:/application_submitted";
//    }
    @GetMapping("/student/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("recruiter", new RecruiterEntity());
        return "student/signup_student";
    }
}
