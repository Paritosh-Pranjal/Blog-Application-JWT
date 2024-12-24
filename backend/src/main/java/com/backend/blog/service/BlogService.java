package com.backend.blog.service;

import com.backend.blog.entity.Blog;

public interface BlogService {
    String createBlog(Blog blogRequest,Long userId) throws Exception;
}
