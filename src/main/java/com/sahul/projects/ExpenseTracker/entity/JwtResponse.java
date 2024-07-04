package com.sahul.projects.ExpenseTracker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {
    private final String jwtToken;

//    public JwtResponse(String jwtToken) {
//        this.jwtToken = jwtToken;
//    }
}
