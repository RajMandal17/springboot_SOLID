package com.interview.employeemanagement.strategy;

/**
 * Strategy Pattern Interface
 * SOLID: Interface Segregation - Small, focused interface
 * SOLID: Open/Closed - New strategies can be added without modifying existing code
 * 
 * Interview Tip: Use this when you have multiple algorithms for the same operation
 */
public interface SalaryStrategy {
    
    /**
     * Calculate adjusted salary based on strategy
     * @param baseSalary - original salary
     * @return adjusted salary
     */
    double calculate(double baseSalary);
    
    /**
     * Get strategy name for logging
     */
    String getStrategyName();
}
