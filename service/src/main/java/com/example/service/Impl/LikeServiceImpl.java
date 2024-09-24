package com.example.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.LikeMapper;
import com.example.model.pojo.Like;
import com.example.service.LikeService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {

    @Autowired
    private UserService userService;

    @Override
    public boolean likePost(Long postId, Long userId) {
        // 先检查是否已经点赞，防止重复点赞
        QueryWrapper<Like> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id", postId).eq("user_id", userId);
        if (this.count(queryWrapper) > 0) {
            return false;  // 已经点赞
        }

        Like like = new Like();
        like.setPostId(postId);
        like.setUserId(userId);
        like.setCreatedTime(LocalDateTime.now());
        return this.save(like);
    }

    @Override
    public boolean unlikePost(Long postId, Long userId) {
        QueryWrapper<Like> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id", postId).eq("user_id", userId);
        return this.remove(queryWrapper);
    }

    @Override
    public Long getLikeCountByPostId(Long postId) {
        return this.count(new QueryWrapper<Like>().eq("post_id", postId));
    }

    @Override
    public String getLikeUsernamesByPostId(Long postId) {
        QueryWrapper<Like> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id", postId);
        List<Like> likes = this.list(queryWrapper);

        // Step 2: 获取所有点赞的用户ID
        List<Long> userIds = likes.stream()
                .map(Like::getUserId)
                .collect(Collectors.toList());

        // 如果没有点赞记录，直接返回空字符串
        if (userIds.isEmpty()) {
            return "";
        }

        // Step 3: 根据用户ID查询对应的用户名
        List<String> usernames = userService.getUsernamesByIds(userIds);

        // Step 4: 将用户名拼接成字符串并返回
        return String.join(",", usernames);
    }
}
