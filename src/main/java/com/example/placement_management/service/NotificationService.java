package com.example.placement_management.service;

import com.example.placement_management.entity.Notification;
import com.example.placement_management.entity.StudentEntity;
import com.example.placement_management.repository.NotificationRepository;
import com.example.placement_management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<StudentEntity> findShortlistedStudentsByJobId(Long jobId) {
        // Retrieve all notifications for the given job ID
        List<Notification> notifications = notificationRepository.findByJobId(jobId);

        // Extract the corresponding students from the notifications
        List<StudentEntity> shortlistedStudents = new ArrayList<>();
        for (Notification notification : notifications) {
            shortlistedStudents.add(notification.getStudent());
        }

        return shortlistedStudents;
    }
}
