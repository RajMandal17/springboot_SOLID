package com.interview.employeemanagement.controller;

import com.interview.employeemanagement.model.Employee;
import com.interview.employeemanagement.model.EmployeeBuilder;
import com.interview.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Employee REST Controller
 * SOLID: Single Responsibility - Only handles HTTP requests/responses
 * SOLID: Dependency Inversion - Depends on EmployeeService interface, not implementation
 * 
 * Interview Tip: Controller should be thin - just delegate to service layer
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * CREATE - POST /api/employees
     * Demonstrates Builder Pattern
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Map<String, Object> payload) {
        // Using Builder Pattern for clean object creation
        Employee employee = new EmployeeBuilder()
                .name((String) payload.get("name"))
                .email((String) payload.get("email"))
                .salary(((Number) payload.get("salary")).doubleValue())
                .department((String) payload.get("department"))
                .build();

        Employee savedEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    /**
     * READ - GET /api/employees/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    /**
     * READ - GET /api/employees
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    /**
     * UPDATE - PUT /api/employees/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employeeDetails) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
     * DELETE - DELETE /api/employees/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Employee deleted successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * GET employees by department
     * GET /api/employees/department/{dept}
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable String department) {
        List<Employee> employees = employeeService.getEmployeesByDepartment(department);
        return ResponseEntity.ok(employees);
    }

    /**
     * Calculate adjusted salary using Strategy Pattern
     * POST /api/employees/calculate-salary
     * Demonstrates Strategy Pattern in action
     */
    @PostMapping("/calculate-salary")
    public ResponseEntity<Map<String, Double>> calculateAdjustedSalary(@RequestBody Map<String, Double> payload) {
        Double baseSalary = payload.get("baseSalary");
        Double adjustedSalary = employeeService.applySalaryStrategy(baseSalary);
        
        Map<String, Double> response = new HashMap<>();
        response.put("baseSalary", baseSalary);
        response.put("adjustedSalary", adjustedSalary);
        
        return ResponseEntity.ok(response);
    }
}
