package com.example.placement_management.entity;

import jakarta.persistence.*;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    private String message;

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public void setMessage(String s) {
        this.message = s;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getJobId() {
        return this.jobId;
    }

    public String getMessage() {
        return this.message;
    }

    public StudentEntity getStudent() {
        return this.student;
    }
}
