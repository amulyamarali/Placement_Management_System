package com.example.placement_management.service;
import com.example.placement_management.entity.StudentDetails;
import com.example.placement_management.repository.StudentDetailsRepository;
import org.springframework.data.domain.Sort;
import com.example.placement_management.entity.JobEntity;
import com.example.placement_management.entity.StudentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.placement_management.repository.JobRepository;
import com.example.placement_management.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentDetailsRepository repository;

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

    public List<JobEntity> listAll(String sortBy) {
        Sort sort = Sort.by(sortBy).ascending();
        return jobRepository.findAll(sort);
    }

    public void applyForJob(Long jobId, StudentEntity student) {
        JobEntity job = jobRepository.findById(jobId).orElse(null);
        if (job != null) {
            List<StudentDetails> applicants = job.getApplicants();
            StudentDetails student1 = repository.getById(student.getId());
            applicants.add(student1);
            job.setApplicants(applicants);
            jobRepository.save(job);
            System.out.println(applicants);
        }
    }

    public void addApplicantToJob(Long jobId, StudentEntity student) {
        Optional<JobEntity> optionalJob = jobRepository.findById(jobId);
        if (optionalJob.isPresent()) {
            JobEntity job = optionalJob.get();
            List<StudentDetails> applicants = job.getApplicants();
            StudentDetails student1 = repository.getById(student.getId());
            applicants.add(student1); // Here, JPA will manage the association
            jobRepository.save(job);
        } else {
            return;
        }
    }
    public List<StudentDetails> getApplicantsForJob(Long jobId) {
        Optional<JobEntity> optionalJob = jobRepository.findById(jobId);
        if (optionalJob.isPresent()) {
            JobEntity job = optionalJob.get();
            return job.getApplicants();
        } else {
            return Collections.emptyList();
        }
    }

    public List<StudentEntity> getShortlistForJob(Long jobId) {
        List<StudentEntity> studentList = studentRepository.findShortlistById(jobId);
        if (!studentList.isEmpty()) {
            return studentList;
        } else {
            return Collections.emptyList();
        }
    }
}
