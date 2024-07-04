package com.sahul.projects.ExpenseTracker.entity;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserModel {
    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message =  "email cannot be blank")
    @Email(message = "please provide a valid email")
    private String email;
    @NotBlank
    @Size(min = 5, message = "password should be at least 5 characters")
    private String password;

    private Long age = 0L;
}
