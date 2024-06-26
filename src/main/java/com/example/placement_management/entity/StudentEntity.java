package com.example.placement_management.entity;

import com.example.placement_management.service.StudentService;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="students")
public class StudentEntity implements ShortlistObserver{

    @Id
    private int id;
//    private Long jobId;
    private String name;
    private String srn;
    private String gender;
    private String branch;
    private String email;
    private double cgpa;
    private int sem;
    private String phone_no;
    private String resume_link;


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
//    public void setJobId(Long jobId) {this.jobId = jobId;}
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

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();
    @Override
    public void notifyShortlisted(StudentEntity student, Long jobId) {
        if (this.equals(student)) {
            Notification notification = new Notification();
            notification.setStudent(this); // Assuming "this" is the current student
            notification.setMessage("You have been shortlisted for job with ID " + jobId);
            this.notifications.add(notification);
        }
    }

    public List<Notification> getNotifications() {
        return this.notifications;
    }

}