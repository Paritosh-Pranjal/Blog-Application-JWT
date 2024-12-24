package com.backend.blog.service.impl;

import com.backend.blog.entity.User;
import com.backend.blog.repository.UserRepository;
import com.backend.blog.service.UserService;
import com.backend.blog.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User createUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception e) {
            System.err.println("Error occurred while saving user: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String loginUser(User user) {
        try {
            // Find user by email and validate the password
            Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
            if (foundUser.isPresent() && passwordEncoder.matches(user.getPassword(), foundUser.get().getPassword())) {
                // Generate the JWT token after successful login
                return jwtUtil.generateToken(user.getEmail());
            }
            return null; // If user not found or password mismatch, return null
        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
            return null;
        }
    }
}
