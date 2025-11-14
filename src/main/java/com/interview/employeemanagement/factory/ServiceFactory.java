package com.interview.employeemanagement.factory;

import com.interview.employeemanagement.service.EmployeeService;
import com.interview.employeemanagement.service.EmployeeServiceImpl;
import com.interview.employeemanagement.repository.EmployeeRepository;

/**
 * Factory Pattern Implementation
 * SOLID: Single Responsibility - Only creates service instances
 * SOLID: Open/Closed - Can add new service types without modifying existing code
 * 
 * Interview Tip: Use Factory when you need to decide which object to create at runtime
 * In real projects with Spring, @Service annotation handles this, but knowing Factory is important!
 */
public class ServiceFactory {

    /**
     * Get appropriate service based on type
     * @param type - service type ("standard", "premium", etc.)
     * @param repository - repository dependency
     * @return EmployeeService implementation
     */
    public static EmployeeService getService(String type, EmployeeRepository repository) {
        if (type == null || type.equalsIgnoreCase("standard")) {
            return new EmployeeServiceImpl(repository);
        }
        
        // Future: Add more service types here
        // if (type.equalsIgnoreCase("premium")) {
        //     return new PremiumEmployeeServiceImpl(repository);
        // }
        // if (type.equalsIgnoreCase("contract")) {
        //     return new ContractEmployeeServiceImpl(repository);
        // }
        
        throw new IllegalArgumentException("Invalid service type: " + type);
    }

    /**
     * Get default service
     */
    public static EmployeeService getDefaultService(EmployeeRepository repository) {
        return getService("standard", repository);
    }
}
