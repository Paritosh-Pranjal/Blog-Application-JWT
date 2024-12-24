package com.backend.blog.service;

import com.backend.blog.entity.User;

public interface UserService {
     User createUser(User user);
     String loginUser(User user);
}
