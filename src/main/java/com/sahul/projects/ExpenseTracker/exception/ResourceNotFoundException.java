package com.sahul.projects.ExpenseTracker.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String msg)
    {
        super(msg);
    }
}
