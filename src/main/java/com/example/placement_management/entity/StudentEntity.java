package com.example.placement_management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student_details")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
    private String srn;
    private String gender;
    private String branch;
    private String email;
    private double cgpa;
    private int sem;
    private String resume_link;
    private Long phoneNo;
    @ManyToOne
    @JoinColumn(name = "job_id")
    private JobEntity appliedJob;

    @ManyToOne
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    private JobEntity shortlistedJobs;

    // Constructor, getters, setters

    public JobEntity getAppliedJob() {
        return appliedJob;
    }

    public JobEntity getShortlistedJob() { return shortlistedJobs; }

    public void setAppliedJob(JobEntity appliedJob) {
        this.appliedJob = appliedJob;
    }

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

    public void setResume_link(String resume_link) {
        this.resume_link = resume_link;
    }

    public void setPhoneNo(Long phoneNo) { this.phoneNo = phoneNo; }

    public Long getPhoneNo() { return phoneNo; }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", srn='" + srn + '\'' +
                ", gender='" + gender + '\'' +
                ", branch='" + branch + '\'' +
                ", email='" + email + '\'' +
                ", cgpa=" + cgpa +
                ", sem=" + sem +
                ", resume_link='" + resume_link + '\'' +
                ", job_id=" + (appliedJob != null ? appliedJob.getId() : "null") +
                ", phone_no=" + phoneNo +
                '}';
    }
}
