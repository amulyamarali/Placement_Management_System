package com.example.placement_management.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="job_details")
public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    public String company;
    public String job_role;
    public String description;
    public long salary;

    @OneToMany(mappedBy = "shortlistedJobs")
    public List<StudentEntity> filteredStudents;
    @OneToMany(mappedBy = "appliedJob")
    private List<StudentEntity> applicants;
    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private RecruiterEntity recruiter;


    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
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

    public long getSalary() {
        return salary;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public JobEntity() {
    }
    public JobEntity(Long id, String company, String job_role, Long salary, String description) {
        this.id = id;
        this.salary = salary;
        this.company = company;
        this.job_role = job_role;
        this.description = description;
    }
    @Override
    public String toString() {
        return "JobEntity{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", job_role='" + job_role + '\'' +
                ", description='" + description + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }

    public List<StudentEntity> getApplicants() {
        return applicants;
    }

    public List<StudentEntity> getfilteredStudents() {
        return filteredStudents;
    }

    public void setApplicants(List<StudentEntity> applicants) {
        this.applicants = applicants;
    }
}
