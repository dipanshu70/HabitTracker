package com.dipanshu.habitTracker.service;

import com.dipanshu.habitTracker.model.Habit;
import com.dipanshu.habitTracker.model.User;
import com.dipanshu.habitTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register a new user
    public User registerUser(String email, String password,String username) {

        // Check if user already exists
        User existingUser = userRepository.findByEmail(email);
        // Create new user
        if(existingUser==null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(passwordEncoder.encode(password));  // Hash the password
            newUser.setUsername(username);

        // Save to database
        return userRepository.save(newUser);}
        // Check if user already exists

        throw new RuntimeException("User already exists with this email");
    }


    // Find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public void saveUser(User user) {userRepository.save(user);}

}