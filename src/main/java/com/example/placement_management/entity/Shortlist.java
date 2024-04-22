package com.example.placement_management.entity;
import com.example.placement_management.entity.ShortlistObserver;
import com.example.placement_management.repository.JobRepository;
import com.example.placement_management.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Shortlist {
    @Autowired
    private JobRepository jobRepository;
    private List<ShortlistObserver> observers = new ArrayList<>();

    // Method to register observers
    public void attach(ShortlistObserver observer) {
        observers.add(observer);
    }
    // Method to notify observers about shortlisting with jobId
    @Autowired
    private NotificationRepository notificationRepository;

    public void notifyShortlisted(StudentEntity student, Long jobId) {
        // Create notification
        Notification notification = new Notification();
        notification.setStudent(student);
        notification.setMessage("You have been shortlisted for job at " + jobRepository.getById(jobId).getCompany());
        notification.setJobId(jobId);

        // Save notification
        notificationRepository.save(notification);
    }

    // Method to shortlist a student for a specific job
    public void shortlistStudent(StudentEntity student, Long jobId) {
        notifyShortlisted(student, jobId);
    }
}
