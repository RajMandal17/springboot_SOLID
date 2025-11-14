package com.interview.employeemanagement.service;

import com.interview.employeemanagement.model.Employee;

import java.util.List;

/**
 * Employee Service Interface
 * SOLID: Dependency Inversion - Controller depends on abstraction, not implementation
 * SOLID: Interface Segregation - Clean contract with only necessary methods
 * 
 * Interview Tip: Always code to interface in layered architecture
 */
public interface EmployeeService {

    /**
     * Create a new employee
     */
    Employee createEmployee(Employee employee);

    /**
     * Get employee by ID
     */
    Employee getEmployeeById(Long id);

    /**
     * Get all employees
     */
    List<Employee> getAllEmployees();

    /**
     * Update employee
     */
    Employee updateEmployee(Long id, Employee employee);

    /**
     * Delete employee
     */
    void deleteEmployee(Long id);

    /**
     * Get employees by department
     */
    List<Employee> getEmployeesByDepartment(String department);

    /**
     * Apply salary strategy and calculate adjusted salary
     * This demonstrates Strategy Pattern
     */
    double applySalaryStrategy(double baseSalary);
}
