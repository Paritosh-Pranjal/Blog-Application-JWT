package com.backend.blog.service.impl;

import com.backend.blog.entity.Blog;
import com.backend.blog.entity.User;
import com.backend.blog.repository.BlogRepository;
import com.backend.blog.repository.UserRepository;
import com.backend.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }


    @Override
    public String createBlog(Blog blogRequest, Long userId) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            blogRequest.setUser(user);
            blogRequest.setCreatedAt(LocalDateTime.now());
            blogRepository.save(blogRequest);
            return "Blog created successfully";
        } else {
            throw new Exception("User not found with ID: " + userId);
        }
    }
}
