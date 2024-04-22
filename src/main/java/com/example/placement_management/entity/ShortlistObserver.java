package com.example.placement_management.entity;

public interface ShortlistObserver {
    void notifyShortlisted(StudentEntity student, Long jobId);
}