package com.interview.employeemanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Employee Entity
 * SOLID: Single Responsibility - Only represents Employee data structure
 */
@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Double salary;

    @Column(nullable = false)
    private String department;

    // Constructor for Builder Pattern
    public Employee(String name, String email, Double salary, String department) {
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.department = department;
    }
}
