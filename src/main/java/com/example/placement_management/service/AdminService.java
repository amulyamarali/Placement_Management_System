package com.example.placement_management.service;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    // Hardcoded admin credentials (replace with database or external authentication provider)
    private static final String ADMIN_USERNAME = "pesu_admin";
    private static final String ADMIN_PASSWORD = "pesu52";

    public boolean authenticate(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }
}