package com.example.placement_management.controller;

import com.example.placement_management.entity.JobEntity;
import com.example.placement_management.entity.StudentEntity;
import com.example.placement_management.repository.StudentRepository;
import com.example.placement_management.service.JobService;
import com.example.placement_management.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/")
public class StudentController {

    @Autowired
    private StudentRepository repo;
    @Autowired
    private JobService jobService;
    @Autowired
    private StudentService studentService;

    @RequestMapping("/student_login")
    public String login() { return "login";}

    @RequestMapping("/student_jobs")
    public String displayJobLists(Model model) {
        List<JobEntity> listJobs = jobService.listAll();
        model.addAttribute("listJobs", listJobs);
        return "student/studentJobs";
    }
    @PostMapping("/application")
    public String apply(@ModelAttribute StudentEntity s) {
        System.out.println(s);
        repo.save(s);

        return "redirect:/studentJobs";
    }

    @GetMapping("/applyJob/{jobId}/{studentId}")
    public String showApplyJobForm(@PathVariable("jobId") Long jobId, @PathVariable int studentId, Model model) {
        JobEntity job = jobService.getById(jobId);
        studentService.applyForJob(studentId, jobId);
        model.addAttribute("job", job);
        model.addAttribute("student", new StudentEntity());
        return "applyJob";
    }
    @GetMapping("/appliedJobs/{studentId}")
    public String showAppliedJobs(@PathVariable int studentId, Model model) {
        List<JobEntity> appliedJobs = studentService.getAppliedJobsForStudent(studentId);
        model.addAttribute("appliedJobs", appliedJobs);
        return "appliedJobs"; // Return the name of your HTML template
    }

}