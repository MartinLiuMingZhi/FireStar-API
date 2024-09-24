package com.example.controller;

import com.example.common.Result;
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
    public Result createPost(@RequestParam Long userId, @RequestParam String content) {
        return Result.success(postService.createPost(userId, content));
    }

    @GetMapping
    public Result getAllPosts() {
        return Result.success(postService.getAllPosts());
    }

    @DeleteMapping("/{id}")
    public Result deletePost(@PathVariable Long id) {
        return Result.success(postService.deletePost(id));
    }
}
