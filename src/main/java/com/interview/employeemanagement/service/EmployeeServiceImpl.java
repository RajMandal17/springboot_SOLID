package com.interview.employeemanagement.service;

import com.interview.employeemanagement.exception.ResourceNotFoundException;
import com.interview.employeemanagement.model.Employee;
import com.interview.employeemanagement.repository.EmployeeRepository;
import com.interview.employeemanagement.strategy.HighSalaryStrategy;
import com.interview.employeemanagement.strategy.LowSalaryStrategy;
import com.interview.employeemanagement.strategy.SalaryStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Employee Service Implementation
 * SOLID: Single Responsibility - Only business logic, no HTTP or DB concerns
 * SOLID: Open/Closed - Can extend with new strategies without modifying this class
 * SOLID: Liskov Substitution - Can be replaced with any EmployeeService implementation
 * SOLID: Dependency Inversion - Depends on EmployeeRepository interface
 * 
 * Interview Tip: This is where all business logic lives
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        // Business logic: validate before saving
        validateEmployee(employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = getEmployeeById(id);
        
        // Update fields
        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setSalary(employeeDetails.getSalary());
        employee.setDepartment(employeeDetails.getDepartment());
        
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }

    @Override
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    /**
     * Strategy Pattern in Action
     * SOLID: Open/Closed - Can add new strategies without modifying this method
     */
    @Override
    public double applySalaryStrategy(double baseSalary) {
        SalaryStrategy strategy;
        
        // Choose strategy based on salary level
        if (baseSalary > 50000) {
            strategy = new HighSalaryStrategy();
        } else {
            strategy = new LowSalaryStrategy();
        }
        
        System.out.println("Applying: " + strategy.getStrategyName());
        return strategy.calculate(baseSalary);
    }

    /**
     * Private validation method
     * SOLID: Single Responsibility - Validation is separate
     */
    private void validateEmployee(Employee employee) {
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }
        if (employee.getEmail() == null || !employee.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (employee.getSalary() == null || employee.getSalary() <= 0) {
            throw new IllegalArgumentException("Salary must be positive");
        }
        if (employee.getDepartment() == null || employee.getDepartment().trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty");
        }
    }
}
