package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.pojo.Messages;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessagesMapper extends BaseMapper<Messages> {
}
