package com.example.placement_management.entity;

public class AdminEntity {
    private static AdminEntity instance;

    // Private constructor to prevent instantiation from outside
    private AdminEntity() {}

    // Method to get the singleton instance
    public static synchronized AdminEntity getInstance() {
        if (instance == null) {
            instance = new AdminEntity();
        }
        return instance;
    }

    // Member variable representing the JobEntity
    private JobEntity jobEntity = new JobEntity();

    // Getter and setter for jobEntity
    public JobEntity getJobEntity() {
        return jobEntity;
    }

    public void setJobEntity(JobEntity jobEntity) {
        this.jobEntity = jobEntity;
    }
}
