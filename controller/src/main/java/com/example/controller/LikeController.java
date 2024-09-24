package com.example.controller;

import com.example.common.Result;
import com.example.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    public Result likePost(@RequestParam Long postId, @RequestParam Long userId) {
        return Result.success(likeService.likePost(postId, userId));
    }

    @DeleteMapping
    public Result unlikePost(@RequestParam Long postId, @RequestParam Long userId) {
        return Result.success(likeService.unlikePost(postId, userId));
    }

    @GetMapping("/{postId}/count")
    public Result getLikeCountByPostId(@PathVariable Long postId) {
        return Result.success(likeService.getLikeCountByPostId(postId));
    }

    @GetMapping("/{postId}/usernames")
    public Result getLikeUsernamesByPostId(@PathVariable Long postId) {
        return Result.success(likeService.getLikeUsernamesByPostId(postId));
    }
}
