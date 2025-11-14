package com.interview.employeemanagement.strategy;

import org.springframework.stereotype.Component;

/**
 * High Salary Strategy Implementation
 * SOLID: Single Responsibility - Only handles high salary calculation
 * Pattern: Strategy Pattern - Implements one algorithm
 * 
 * Interview Tip: Used for employees with salary > 50000
 */
@Component
public class HighSalaryStrategy implements SalaryStrategy {

    private static final double BONUS_MULTIPLIER = 1.2; // 20% bonus

    @Override
    public double calculate(double baseSalary) {
        return baseSalary * BONUS_MULTIPLIER;
    }

    @Override
    public String getStrategyName() {
        return "High Salary Strategy (20% Bonus)";
    }
}
