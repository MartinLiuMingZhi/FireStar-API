package com.example.controller;

import com.example.model.pojo.Comment;
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
    public boolean addComment(@RequestParam Long postId, @RequestParam Long userId, @RequestParam String content) {
        return commentService.addComment(postId, userId, content);
    }

    @GetMapping("/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @DeleteMapping("/{id}")
    public boolean deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }
}
