package com.dipanshu.habitTracker.controller;

import com.dipanshu.habitTracker.Jwt.JwtTokenProvider;
import com.dipanshu.habitTracker.model.User;
import com.dipanshu.habitTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
@Autowired
private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public User createUser (@RequestBody User newUser){
    return userService.registerUser(newUser.getEmail(), newUser.getPassword(), newUser.getUsername());
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser (@RequestBody User user){
        try{authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
            User existinguser =userService.findByEmail(user.getEmail());
            String jwt= jwtTokenProvider.generateToken(existinguser.getId(), existinguser.getEmail());
        return new ResponseEntity<>(jwt, HttpStatus.OK);

    }
        catch (Exception e){
           System.out.println("Exception occurred while createAuthenticationToken " );
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }




}
