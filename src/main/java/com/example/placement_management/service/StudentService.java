package com.example.placement_management.service;

import com.example.placement_management.entity.StudentDetails;
import com.example.placement_management.repository.StudentDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
    @Autowired
    private StudentDetailsRepository studentRepository;

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        // Check if a student with the given username exists
        return studentRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmailId(String emailId) {
        // Check if a student with the given email ID exists
        return studentRepository.existsByEmailId(emailId);
    }

    @Transactional
    public void save(StudentDetails student) {
        // Save the student to the database
        studentRepository.save(student);
    }
}
