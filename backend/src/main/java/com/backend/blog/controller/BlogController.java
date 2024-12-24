package com.backend.blog.controller;

import com.backend.blog.entity.Blog;
import com.backend.blog.entity.User;
import com.backend.blog.repository.BlogRepository;
import com.backend.blog.repository.UserRepository;
import com.backend.blog.service.BlogService;
import com.backend.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/blogs")
public class BlogController {

    private final BlogService blogService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BlogRepository blogRepository;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/create-blog")
    public ResponseEntity<String> createBlog(@RequestBody Blog blogRequest) {
        try {
            // Retrieve the authenticated user from the SecurityContext
            User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = authenticatedUser.getId();
            String responseMessage = blogService.createBlog(blogRequest, userId);
            return ResponseEntity.ok(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred while saving blog: " + e.getMessage());
        }
    }
}
