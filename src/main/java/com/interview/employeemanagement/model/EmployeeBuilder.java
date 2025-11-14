package com.interview.employeemanagement.model;

/**
 * Builder Pattern Implementation
 * Purpose: Create Employee objects in a clean, readable way
 * SOLID: Single Responsibility - Only handles object construction
 * 
 * Interview Tip: This pattern is great for objects with many fields
 */
public class EmployeeBuilder {

    private String name;
    private String email;
    private Double salary;
    private String department;

    public EmployeeBuilder name(String name) {
        this.name = name;
        return this;
    }

    public EmployeeBuilder email(String email) {
        this.email = email;
        return this;
    }

    public EmployeeBuilder salary(Double salary) {
        this.salary = salary;
        return this;
    }

    public EmployeeBuilder department(String department) {
        this.department = department;
        return this;
    }

    public Employee build() {
        return new Employee(name, email, salary, department);
    }
}
