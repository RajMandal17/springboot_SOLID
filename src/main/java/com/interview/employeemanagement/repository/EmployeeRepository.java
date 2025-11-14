package com.interview.employeemanagement.repository;

import com.interview.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Employee Repository Interface
 * SOLID: Single Responsibility - Only handles database operations
 * SOLID: Dependency Inversion - Service depends on this interface, not implementation
 * 
 * Interview Tip: Spring Data JPA provides implementation automatically
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Custom query methods
    Optional<Employee> findByEmail(String email);
    
    List<Employee> findByDepartment(String department);
    
    List<Employee> findBySalaryGreaterThan(Double salary);
}
