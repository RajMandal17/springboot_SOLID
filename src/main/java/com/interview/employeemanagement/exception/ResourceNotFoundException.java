package com.interview.employeemanagement.exception;

/**
 * Custom Exception for Resource Not Found
 * SOLID: Single Responsibility - Handles one type of exception
 * 
 * Interview Tip: Custom exceptions make error handling cleaner
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
