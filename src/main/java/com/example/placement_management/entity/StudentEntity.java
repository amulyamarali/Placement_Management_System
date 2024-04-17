package com.example.placement_management.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    private int id;
    private String name;
    private String srn;
    private String gender;
    private String branch;
    private String email;
    private double cgpa;
    private int sem;
    private String phone_no;
    private String resume_link;
    private Long jobId;
    @ManyToMany(cascade = {CascadeType.REMOVE})
    @JoinTable(name = "student_job",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    private List<JobEntity> appliedJobs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrn() {
        return srn;
    }

    public void setSrn(String srn) {
        this.srn = srn;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public int getSem() {
        return sem;
    }

    public void setSem(int sem) {
        this.sem = sem;
    }

    public String getResume_link() {
        return resume_link;
    }

    public void setPhoneNo(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getPhoneNo() {
        return phone_no;
    }

    public void setResume_link(String resume_link) {
        this.resume_link = resume_link;
    }

    public void setJobId(Long jobId) { this.jobId = jobId; }
    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", srn='" + srn + '\'' +
                ", gender='" + gender + '\'' +
                ", branch='" + branch + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", email='" + email + '\'' +
                ", cgpa=" + cgpa +
                ", sem=" + sem +
                ", resume_link='" + resume_link + '\'' +
                '}';
    }

    public void setAppliedJobs(JobEntity appliedJob) {
        if(this.appliedJobs == null) {
            appliedJobs = new ArrayList<>();
            appliedJobs.add(appliedJob);
        }
        else {
            this.appliedJobs.add(appliedJob);
        }

    }
    public List<JobEntity> getAppliedJobs() {
        return appliedJobs;
    }
}