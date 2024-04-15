package com.example.placement_management.repository;

import com.example.placement_management.entity.JobEntity;
import com.example.placement_management.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<JobEntity, Long> {
    List<JobEntity> findByRecruiterIdAndId(Long jobId);
}
