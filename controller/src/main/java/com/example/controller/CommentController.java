package com.example.controller;

import com.example.common.Result;
import com.example.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public Result addComment(@RequestParam Long postId, @RequestParam Long userId, @RequestParam String content) {
        return Result.success(commentService.addComment(postId, userId, content));
    }

    @GetMapping("/{postId}")
    public Result getCommentsByPostId(@PathVariable Long postId) {
        return Result.success(commentService.getCommentsByPostId(postId));
    }

    @DeleteMapping("/{id}")
    public Result deleteComment(@PathVariable Long id) {
        return Result.success(commentService.deleteComment(id));
    }
}
 