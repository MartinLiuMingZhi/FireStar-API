package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.pojo.Post;

import java.util.List;

public interface PostService extends IService<Post> {

    boolean createPost(Long userId, String content);

    List<Post> getAllPosts();

    boolean deletePost(Long id);
}
