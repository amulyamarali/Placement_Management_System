package com.example.placement_management.controller;

import com.example.placement_management.entity.*;
import com.example.placement_management.repository.JobRepository;
import com.example.placement_management.repository.StudentRepository;

import com.example.placement_management.repository.RecruiterRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class RecruiterController {
    @Autowired
    private StudentRepository repo;
    @Autowired
    private RecruiterRepository repo1;
    @Autowired
    private JobRepository jobRepository;

    @RequestMapping("/recruiter_login")
    public String showlogin() {
        return "recruiter/login_recruiter";
    }

    @PostMapping("/recruiterLogin")
    public String login(@RequestParam("id") String id, @RequestParam("password") String password,
                        Model model) {
        Optional<RecruiterEntity> optionalRecruiter = repo1.findById(id);
        if (optionalRecruiter.isPresent()) {
            RecruiterEntity recruiter = optionalRecruiter.get();
            if (password.equals(recruiter.getPassword())) {
                String recruiterId = recruiter.getId();
                model.addAttribute("recruiterId", recruiterId);
                return "redirect:/recruiter/" + recruiterId;
            } else {
                model.addAttribute("loginError", "Invalid username or password.");
            }
        } else {
            model.addAttribute("loginError", "Invalid username or password.");
        }
        return "recruiter/login_recruiter";
    }


    @RequestMapping("/recruiter/{recruiterId}")
    public String showRecruiterPage(Model model, @PathVariable String recruiterId) {
        Optional<RecruiterEntity> recruiter= repo1.findById(recruiterId);
        RecruiterEntity recruiter1 = recruiter.get();
        Long jobId = recruiter1.getJobId();
        JobEntity job = jobRepository.getById(jobId);
        List<StudentEntity> applicants = new ArrayList<>();
        List<StudentDetails> applied = job.getApplicants();
        for(StudentDetails student : applied) {
            int studentId = student.getId();
            StudentEntity student1 = repo.getById(studentId);
            applicants.add(student1);
        }
//        List<StudentEntity> allStudents = repo.findByJobId(jobId);
        model.addAttribute("students", applicants);
        model.addAttribute("recruiterId", recruiterId);
        return "recruiter/recruiter";
    }

    @PostMapping("/recruiter/filter/{recruiterId}")
    public String filterStudents(@RequestParam("cgpa") double cgpa, Model model, @PathVariable String recruiterId) {
        List<StudentEntity> filteredStudents = repo.findByCgpaGreaterThanEqual(cgpa);
        RecruiterEntity recruiter = repo1.getById(recruiterId);
        Long jobId = recruiter.getJobId();
        for(StudentEntity student: filteredStudents) {
            shortlistSubject.attach(student);
            shortlistSubject.shortlistStudent(student, jobId);
        }
        model.addAttribute("students", filteredStudents);
        return "recruiter/recruiter";
    }

    @Autowired
    private Shortlist shortlistSubject;
    @PostMapping("/shortlist")
    public String shortlistStudent(@RequestParam("studentId") int studentId, @RequestParam("jobId") Long jobId) {
        // Retrieve student details and job details based on IDs
        StudentEntity student = repo.getById(studentId);
        // Shortlist the student for the job
        shortlistSubject.attach(student);
        shortlistSubject.shortlistStudent(student, jobId);
        return "redirect:/student";
    }
//    @GetMapping("/recruiter/signup")
//    public String showSignupForm(Model model) {
//        model.addAttribute("recruiter", new RecruiterEntity());
//        return "recruiter/signup_recruiter";
//    }

}
