package com.sahul.projects.ExpenseTracker.exception;

public class ItemExistsException extends RuntimeException{
    public ItemExistsException(String msg)
    {
        super(msg);
    }
}
