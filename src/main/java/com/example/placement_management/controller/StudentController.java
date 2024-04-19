package com.example.placement_management.controller;

import com.example.placement_management.entity.JobEntity;
import com.example.placement_management.entity.RecruiterEntity;
import com.example.placement_management.entity.StudentDetails;
import com.example.placement_management.entity.StudentEntity;
import com.example.placement_management.repository.StudentRepository;
import com.example.placement_management.repository.StudentDetailsRepository;
import com.example.placement_management.service.JobService;
import com.example.placement_management.service.StudentService;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class StudentController {

    @Autowired
    private StudentRepository repo;
    @Autowired
    private JobService jobService;
    @Autowired
    private StudentDetailsRepository repository;
    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String landing() {
        return "landing";
    }
    @GetMapping("/student/signup")
    public String showSignupForm(Model model) {
//        model.addAttribute("recruiter", new RecruiterEntity());
        return "student/signup_student";
    }
    @RequestMapping("/signup")
    public String signup(@ModelAttribute StudentDetails student, Model model) {
        // Check if the student already exists
        if (studentService.existsByEmailId(student.getEmailId())) {
             System.out.println("duplicate");
             model.addAttribute("errorMessage", "Account already exists. Please login");
             int id = student.getId();
             System.out.println(id);
             return "redirect:/student/signup";
        } else {
            // Student does not exist, proceed with signup
            repository.save(student);
            int studentId = student.getId();
            return "redirect:/student_jobs/"+studentId;
        }
//        repository.save(student);
//        int studentId = student.getId();
//        return "redirect:/student_jobs/"+studentId;
    }

    @GetMapping("/student_login")
    public String Showlogin() {
        return "student/login_student";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
            Model model) {
        StudentDetails student = repository.findByUsername(username);
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
        StudentEntity student = repo.getById(studentId);
        List<JobEntity> notAppliedJobs = new ArrayList<>();

        if (student.getId() == 0 ){
//            student = optionalStudent.get();
            List<JobEntity> appliedJobs = student.getAppliedJobs();
            List<JobEntity> listJobs = jobService.listAll();

            // Iterate through all available jobs
            for (JobEntity job : listJobs) {
                // Check if the job is not already applied for
                if (!appliedJobs.contains(job)) {
                    notAppliedJobs.add(job);
                }
            }
        }
        List<JobEntity> listJobs = jobService.listAll();
        if(notAppliedJobs.isEmpty()) {
            model.addAttribute("errorMessage", "No jobs to apply");
        }
        model.addAttribute("listJobs", notAppliedJobs);
        model.addAttribute("studentId", studentId);
        return "redirect:/student_jobs/" + studentId;
    }

    @RequestMapping("/applyJob/{studentId}/{jobId}")
    public String showApplyJobForm(@PathVariable("jobId") Long jobId, @PathVariable int studentId, @ModelAttribute StudentEntity student, Model model) {
        JobEntity job = jobService.getById(jobId);
        student.setId(studentId);
        student.setJobId(jobId);

        boolean alreadyApplied = false;
        for (JobEntity appliedJob : student.getAppliedJobs()) {
            if (appliedJob.getId().equals(jobId)) {
                alreadyApplied = true;
                break;
            }
        }
        if (alreadyApplied) {
             model.addAttribute("errorMessage", "You have already applied for this job.");
             return "redirect:/student_jobs" + studentId;
        }
        student.setAppliedJobs(job);
        jobService.applyForJob(jobId, student);
        model.addAttribute("job", job);
        model.addAttribute("student", student);
        return "student/applyJob";
    }

    @PostMapping("/submit_application/{studentId}")
    public String submitApplication(@PathVariable("studentId") Long studentId, @ModelAttribute StudentEntity student) {
        repo.save(student);
        return "redirect:/application_submitted";
    }
}
