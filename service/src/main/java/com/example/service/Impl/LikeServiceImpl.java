package com.example.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.LikeMapper;
import com.example.model.pojo.Like;
import com.example.service.LikeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {
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
}
