package com.interview.employeemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application Class
 * This is the entry point for Spring Boot application
 */
@SpringBootApplication
public class EmployeeManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
        System.out.println("✅ Employee Management System Started Successfully!");
        System.out.println("📍 Server running on: http://localhost:8080");
        System.out.println("🗄️ Database: MySQL (employeedb)");
    }
}
