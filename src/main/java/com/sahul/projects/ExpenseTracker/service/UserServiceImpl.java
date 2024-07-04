package com.sahul.projects.ExpenseTracker.service;

import com.sahul.projects.ExpenseTracker.entity.User;
import com.sahul.projects.ExpenseTracker.entity.UserModel;
import com.sahul.projects.ExpenseTracker.exception.EmailAlreadyExistsException;
import com.sahul.projects.ExpenseTracker.exception.ResourceNotFoundException;
import com.sahul.projects.ExpenseTracker.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public User createUser(UserModel userModel) {
        if(userRepository.existsByEmail(userModel.getEmail()))throw new EmailAlreadyExistsException("provided email already exists,please provide other email");
        User user = new User();
        BeanUtils.copyProperties(userModel, user);
        System.out.println(user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        return userRepository.save(user);

    }

    @Override
    public User ReadUser() {
        Long id = getLoggedInUser().getId();
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("no user found with the given id"));
    }

    @Override
    public User UpdateUser(User user) throws ResourceNotFoundException{
        User currUser = ReadUser();
        currUser.setName(user.getName() != null ? user.getName() : currUser.getName());
        currUser.setEmail(user.getEmail() != null ? user.getEmail(): currUser.getEmail());
        currUser.setPassword(user.getPassword() != null ? bCryptPasswordEncoder.encode(user.getPassword()) : currUser.getPassword());
        System.out.println(currUser.getPassword()+ "    "+user.getPassword() );
        currUser.setAge(user.getAge()!= null ? user.getAge() : currUser.getAge());
        return userRepository.save(currUser);

    }

    @Override
    public void deleteUser() {
        User user = ReadUser();
        userRepository.delete(user);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("user not found with the given email "+email));
    }
}
