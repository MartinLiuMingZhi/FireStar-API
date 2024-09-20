package com.example.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.CommentMapper;
import com.example.model.pojo.Comment;
import com.example.service.CommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>  implements CommentService {
    @Override
    public boolean addComment(Long postId, Long userId, String content) {
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setCreatedTime(LocalDateTime.now());
        return this.save(comment);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return this.baseMapper.selectList(new QueryWrapper<Comment>().eq("post_id", postId));
    }

    @Override
    public boolean deleteComment(Long id) {
        return this.removeById(id);
    }
}
