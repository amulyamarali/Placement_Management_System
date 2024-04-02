package com.example.placement_management.service;

import com.example.placement_management.entity.JobEntity;
import com.example.placement_management.entity.StudentEntity;
import com.example.placement_management.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity>  listAll() {
        return jobRepository.findAll();
    }

    public void uploadJob(JobEntity job) {
        jobRepository.save(job);
    }

    public JobEntity getById(long id) {
        return jobRepository.findById(id).orElse(null);
    }

    public void delete(long id) {
        jobRepository.deleteById(id);
    }

    public void applyForJob(Long jobId, StudentEntity student) {
        // Add validation logic if necessary
        // Fetch job by id, then add student to job's list of applicants
        JobEntity job = jobRepository.findById(jobId).orElse(null);
        if (job != null) {
            job.getApplicants().add(student);
            jobRepository.save(job);
        }
    }

    public void addApplicantToJob(Long jobId, StudentEntity student) {
        Optional<JobEntity> optionalJob = jobRepository.findById(jobId);
        if (optionalJob.isPresent()) {
            JobEntity job = optionalJob.get();
            List<StudentEntity> applicants = job.getApplicants();
            applicants.add(student); // Here, JPA will manage the association
            jobRepository.save(job);
        } else {
            return;
        }
    }
    public List<StudentEntity> getApplicantsForJob(Long jobId) {
        Optional<JobEntity> optionalJob = jobRepository.findById(jobId);
        if (optionalJob.isPresent()) {
            JobEntity job = optionalJob.get();
            return job.getApplicants();
        } else {
            return Collections.emptyList();
        }
    }
}
