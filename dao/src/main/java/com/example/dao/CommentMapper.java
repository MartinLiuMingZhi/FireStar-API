package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
