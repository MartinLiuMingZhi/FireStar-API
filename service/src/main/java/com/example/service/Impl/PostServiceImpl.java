package com.example.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.PostMapper;
import com.example.model.pojo.Post;
import com.example.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    /**
     * 发布动态
     * @param userId
     * @param content
     * @return
     */
    @Override
    public boolean createPost(Long userId, String content) {
        Post post = new Post();
        post.setUserId(userId);
        post.setContent(content);
        post.setCreatedTime(LocalDateTime.now());
        return this.save(post);
    }

    /**
     * 获取所有动态
     * @return
     */
    @Override
    public List<Post> getAllPosts() {
        return this.baseMapper.selectList(null);  // 查询所有
    }

    /**
     * 删除动态
     * @param id
     * @return
     */
    @Override
    public boolean deletePost(Long id) {
        return this.removeById(id);
    }
}
