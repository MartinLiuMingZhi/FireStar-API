package com.example.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
