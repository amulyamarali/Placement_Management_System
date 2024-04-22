package com.example.placement_management.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="job_details")
public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long jobId;
    public String company;
    public String job_role;
    public String description;
    public long salary;
    public String recruiter_id;
    public String deadline;
    public String recruiter_credentials;
    private String tier;

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    @ManyToMany(mappedBy = "appliedJobs")
    private List<StudentDetails> applicants;

    public void setId(long id) {
        this.jobId = id;
    }
    public Long getId() {
        return jobId;
    }
    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setJob_role(String job_role) {
        this.job_role = job_role;
    }

    public String getJob_role() {
        return job_role;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public Long getSalary() {
        return salary;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    public void setRecruiter_id(String recruiter_id){
        this.recruiter_id = recruiter_id;
    }

    public String getRecruiter_id() {
        return recruiter_id;
    }
    public String getRecruiter_credentials() {
        return recruiter_credentials;
    }

    public void setRecruiter_credentials(String recruiter_credentials) {
        this.recruiter_credentials = recruiter_credentials;
    }

    public void setDeadline(String deadline){
        this.deadline = deadline;
    }

    public String getDeadline() {
        return deadline;
    }

    public JobEntity() {
    }
    public JobEntity(Long id, String company, String job_role, Long salary, String description,String recruiter_id,String recruiter_credentials,String deadline) {
        this.jobId = id;
        this.salary = salary;
        this.company = company;
        this.job_role = job_role;
        this.description = description;
        this.recruiter_id = recruiter_id;
        this.recruiter_credentials = recruiter_credentials;
        this.deadline = deadline;
    }
    @Override
    public String toString() {
        return "JobEntity{" +
                "id=" + jobId +
                ", company='" + company + '\'' +
                ", job_role='" + job_role + '\'' +
                ", description='" + description + '\'' +
                ", salary='" + salary + '\'' +
                ", recruiter_id='" + recruiter_id + '\'' +
                ", recruiter_credentials='" + recruiter_credentials + '\'' +
                ", deadline='"+deadline+'\'' +
                '}';
    }

    public List<StudentDetails> getApplicants() {
        return this.applicants;
    }

    public void setApplicants(List<StudentDetails> applicants) {
        this.applicants = applicants;
    }
}