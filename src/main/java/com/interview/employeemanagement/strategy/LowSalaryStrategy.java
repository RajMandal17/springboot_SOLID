package com.interview.employeemanagement.strategy;

import org.springframework.stereotype.Component;

/**
 * Low Salary Strategy Implementation
 * SOLID: Single Responsibility - Only handles low salary calculation
 * Pattern: Strategy Pattern - Implements one algorithm
 * 
 * Interview Tip: Used for employees with salary <= 50000
 */
@Component
public class LowSalaryStrategy implements SalaryStrategy {

    private static final double ADJUSTMENT_MULTIPLIER = 0.9; // 10% reduction

    @Override
    public double calculate(double baseSalary) {
        return baseSalary * ADJUSTMENT_MULTIPLIER;
    }

    @Override
    public String getStrategyName() {
        return "Low Salary Strategy (10% Adjustment)";
    }
}
