package com.example.placement_management.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student_signup")
public class StudentDetails  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email_id", unique = true)
    private String emailId;

    public StudentDetails() {
    }

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "student_job",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    private List<JobEntity> appliedJobs;

    public StudentDetails(String username, String password, String email_id) {
        this.username = username;
        this.password = password;
        this.emailId = email_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmail_id(String email_id) {
        this.emailId = email_id;
    }

    public void setAppliedJobs(JobEntity appliedJob, StudentDetails student) {
            student.getAppliedJobs().add(appliedJob);
    }
    public List<JobEntity> getAppliedJobs() {
        return this.appliedJobs;
    }

}
