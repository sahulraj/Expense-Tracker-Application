package com.sahul.projects.ExpenseTracker.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
@Data
public class ErrorObj {
    private Integer statusCode;
    private String msg;
    private Date timestamp;
}
