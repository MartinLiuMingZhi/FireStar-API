package com.example.controller;

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
    public boolean likePost(@RequestParam Long postId, @RequestParam Long userId) {
        return likeService.likePost(postId, userId);
    }

    @DeleteMapping
    public boolean unlikePost(@RequestParam Long postId, @RequestParam Long userId) {
        return likeService.unlikePost(postId, userId);
    }

    @GetMapping("/{postId}/count")
    public Long getLikeCountByPostId(@PathVariable Long postId) {
        return likeService.getLikeCountByPostId(postId);
    }

}
