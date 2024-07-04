package com.sahul.projects.ExpenseTracker.security;

import com.sahul.projects.ExpenseTracker.entity.User;
import com.sahul.projects.ExpenseTracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User ExistingUser = userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("user not found with the given id "+email));
        return new org.springframework.security.core.userdetails.User(ExistingUser.getEmail(), ExistingUser.getPassword(), new ArrayList<>());
    }
}
