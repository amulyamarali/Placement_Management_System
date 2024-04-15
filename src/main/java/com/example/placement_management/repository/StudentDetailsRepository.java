package com.example.placement_management.repository;

import com.example.placement_management.entity.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StudentDetailsRepository extends JpaRepository<StudentDetails,Integer> {
    StudentDetails findByUsername(String username);
}

