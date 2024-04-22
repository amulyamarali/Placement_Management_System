package com.example.placement_management.repository;

import com.example.placement_management.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
    List<Notification> findByJobId(Long jobId);
}
