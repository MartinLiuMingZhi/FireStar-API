package com.example.controller;

import com.example.model.pojo.Post;
import com.example.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public boolean createPost(@RequestParam Long userId, @RequestParam String content) {
        return postService.createPost(userId, content);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @DeleteMapping("/{id}")
    public boolean deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }
}
