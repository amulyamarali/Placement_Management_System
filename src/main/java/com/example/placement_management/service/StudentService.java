package com.example.placement_management.service;

import com.example.placement_management.entity.JobEntity;
import com.example.placement_management.repository.JobRepository;
import com.example.placement_management.repository.StudentRepository;
import com.example.placement_management.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JobRepository jobRepository;

    private StudentEntity student;

    public List<JobEntity> getAppliedJobsForStudent(int studentId) {
        StudentEntity student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            return student.getAppliedJobs();
        } else {
            return Collections.emptyList();
        }
    }
    public void applyForJob(int studentId, Long jobId) {
        // Fetch student from the database
        StudentEntity student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            // Handle case when student is not found
            return;
        }

        // Fetch job from the database
        JobEntity job = jobRepository.findById(jobId).orElse(null);
        if (job == null) {
            // Handle case when job is not found
            return;
        }

        // Add job to appliedJobs list of the student
        List<JobEntity> appliedJobs = student.getAppliedJobs();
        appliedJobs.add(job);
        student.setAppliedJobs(appliedJobs);

        // Save the updated student entity
        studentRepository.save(student);
    }

    public List<JobEntity> listAllJobs(int studentId) {
        List<JobEntity> allJobs = jobRepository.findAll();
        List<JobEntity> appliedJobs = student.getAppliedJobs();
        // Remove applied jobs from the list of all jobs
        allJobs.removeAll(appliedJobs);
        return allJobs;
    }
}