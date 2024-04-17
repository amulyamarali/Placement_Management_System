package com.example.placement_management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recruiter_details")
public class RecruiterEntity {
    @Id
    private String id;

    @Column(nullable = false)
    private String password;
    private String companyName;
    private Long jobId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public RecruiterEntity() {

    }
//    public RecruiterEntity(Long id, String password, String companyName, Long jobId) {
//        this.id = id;
//        this.jobId = jobId;
//        this.companyName = companyName;
//        this.password = password;
//    }
    @Override
    public String toString() {
        return "RecruiterEntity{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", companyName='" + companyName + '\'' +
                ", jobId='" + jobId + '\'' +
                '}';
    }
}