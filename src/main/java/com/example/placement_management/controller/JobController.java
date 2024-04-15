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

import com.example.placement_management.entity.JobEntity;
import com.example.placement_management.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class JobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private JobRepository jobRepository;
    public List<JobEntity> listAll() {
        return jobService.listAll();
    }

    @GetMapping("/uploadJob")
    public String showUploadJobForm(Model model) {
        model.addAttribute("job", new JobEntity());
        return "admin/uploadJobs";
    }

    @PostMapping("/uploadJob")
    public String uploadJob(@ModelAttribute("job") JobEntity job) {
        jobService.uploadJob(job);
        return "redirect:/";
    }

    @PostMapping("/editJob/{jobId}")
    public String editJob(@ModelAttribute JobEntity jobEntity, @PathVariable Long jobId) {
        JobEntity existingJob = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + jobId));
        existingJob.setJob_role(jobEntity.getJob_role());
        existingJob.setSalary(jobEntity.getSalary());
        existingJob.setDescription(jobEntity.getDescription());
        // Update other fields as needed
        jobRepository.save(existingJob);
        return "redirect:/showJobs";
    }
    @GetMapping("/recruiter/job/{jobId}/applicants")
    public String getApplicantsForJobAndRecruiter(@PathVariable Long jobId, Model model) {
        List<StudentEntity> applicants = jobService.getApplicantsByJobIdAndRecruiterId(jobId);
        model.addAttribute("applicants", applicants);
        return "recruiter/applicants"; // Return the name of your view
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
}
