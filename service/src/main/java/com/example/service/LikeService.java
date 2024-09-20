package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.pojo.Like;

public interface LikeService extends IService<Like> {
    boolean likePost(Long postId, Long userId);

    boolean unlikePost(Long postId, Long userId);

    Long getLikeCountByPostId(Long postId);
}
