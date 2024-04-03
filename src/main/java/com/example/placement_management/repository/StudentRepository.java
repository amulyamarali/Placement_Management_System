package com.example.placement_management.repository;

import com.example.placement_management.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface StudentRepository extends JpaRepository<StudentEntity,Integer> {
    List<StudentEntity> findByCgpaGreaterThanEqual(double cgpa);
}
