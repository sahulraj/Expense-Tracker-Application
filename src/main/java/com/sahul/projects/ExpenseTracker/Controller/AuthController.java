package com.sahul.projects.ExpenseTracker.Controller;

import com.sahul.projects.ExpenseTracker.entity.AuthModel;
import com.sahul.projects.ExpenseTracker.entity.JwtResponse;
import com.sahul.projects.ExpenseTracker.entity.User;
import com.sahul.projects.ExpenseTracker.entity.UserModel;
import com.sahul.projects.ExpenseTracker.security.CustomUserDetailsService;
import com.sahul.projects.ExpenseTracker.service.UserService;
import com.sahul.projects.ExpenseTracker.util.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService userDetailsService; //to generate jwt token we need user details
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @PostMapping("/login")
    public ResponseEntity<JwtResponse>login(@RequestBody AuthModel authModel) throws Exception
    {
       authenticate(authModel.getEmail(), authModel.getPassword());
       //generate the token using the logged in user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(authModel.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return new ResponseEntity<JwtResponse>(new JwtResponse(token),HttpStatus.OK);
    }
    private void authenticate(String email, String password)throws Exception
    {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }
        catch (DisabledException e)
        {
            throw new Exception("user not found");
        }
        catch (BadCredentialsException e)
        {
            throw new Exception("Bad Credentials");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<User>createUser(@Valid @RequestBody UserModel userModel)
    {

        User user = userService.createUser(userModel);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }
}
