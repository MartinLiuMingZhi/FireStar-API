package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.pojo.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {
    boolean addComment(Long postId, Long userId, String content);

    List<Comment> getCommentsByPostId(Long postId);

    boolean deleteComment(Long id);
}
