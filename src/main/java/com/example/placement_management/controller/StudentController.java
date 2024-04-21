package com.example.placement_management.controller;

import com.example.placement_management.entity.*;
import com.example.placement_management.repository.StudentRepository;
import com.example.placement_management.repository.StudentDetailsRepository;
import com.example.placement_management.service.JobService;
import com.example.placement_management.service.StudentService;
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
        StudentDetails StudentEntity = repository.getById(studentId);

        List<JobEntity> notAppliedJobs = new ArrayList<>();
        List<JobEntity> listJobs = jobService.listAll();

//      if student details already exists (logging in)
        if (StudentEntity.getId() > 0){
            List<JobEntity> appliedJobs = StudentEntity.getAppliedJobs();

            // Iterate through all available jobs
            for (JobEntity job : listJobs) {
                // Check if the job is not already applied for
                if (!appliedJobs.contains(job)) {
                    notAppliedJobs.add(job);
                }
            }
            if(notAppliedJobs.isEmpty()) {
                model.addAttribute("errorMessage", "No jobs to apply");
            }
            else {
                model.addAttribute("listJobs", notAppliedJobs);
            }
        }
        else { //       for new students
            model.addAttribute("listJobs", listJobs);
        }
        model.addAttribute("studentId", studentId);
        return "student/studentJobs";
    }

    @RequestMapping("/applyJob/{studentId}/{jobId}")
    public String showApplyJobForm(@PathVariable("jobId") Long jobId, @PathVariable int studentId, Model model) {
        model.addAttribute("studentId", studentId);
        model.addAttribute("jobId", jobId);
        return "student/applyJob";
    }

    @PostMapping("/submit_application/{studentId}/{jobId}")
    public String submitApplication(@PathVariable("jobId") Long jobId, @PathVariable int studentId, @ModelAttribute StudentEntity student, Model model) {
        JobEntity job = jobService.getById(jobId);
        System.out.println(student);
        student.setId(studentId);
        StudentDetails student1 = repository.getById(studentId);
        student1.setAppliedJobs(job, student1);
        repository.save(student1);
        repo.save(student);
        System.out.println(student1.getAppliedJobs());
        model.addAttribute("student", student);
        return "redirect:/student_jobs/"+studentId;
    }
}












//        student.setJobId(jobId);
//        boolean alreadyApplied = false;
//        if (student.getAppliedJobs() != null) {
//            System.out.println(student.getAppliedJobs());
//            for (JobEntity appliedJob : student.getAppliedJobs()) {
//                if (appliedJob.getId().equals(jobId)) {
//                    alreadyApplied = true;
//                    break;
//                }
//            }
//        }
//        else {
//            student.setAppliedJobs(job);
//            jobService.applyForJob(jobId, student);
//            model.addAttribute("job", job);
//        }
//        if (alreadyApplied) {
//             model.addAttribute("errorMessage", "You have already applied for this job.");
//             return "redirect:/student_jobs" + studentId;
//        }