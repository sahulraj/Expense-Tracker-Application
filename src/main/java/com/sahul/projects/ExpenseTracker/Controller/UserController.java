package com.sahul.projects.ExpenseTracker.Controller;

import com.sahul.projects.ExpenseTracker.entity.User;
import com.sahul.projects.ExpenseTracker.entity.UserModel;
import com.sahul.projects.ExpenseTracker.exception.EmailAlreadyExistsException;
import com.sahul.projects.ExpenseTracker.exception.ResourceNotFoundException;
import com.sahul.projects.ExpenseTracker.repositories.UserRepository;
import com.sahul.projects.ExpenseTracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<User>readUser()
    {

        return new ResponseEntity<User>(userService.ReadUser(), HttpStatus.OK);
    }
    @PutMapping("/profile")
    public ResponseEntity<User>updateUser(@RequestBody  User user)
    {

        return new ResponseEntity<>(userService.UpdateUser(user), HttpStatus.OK);
    }
    @DeleteMapping("/deactivate")
    public ResponseEntity<HttpStatus>delete()throws ResourceNotFoundException{

        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
