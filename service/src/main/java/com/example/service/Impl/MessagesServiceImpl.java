package com.example.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.MessagesMapper;
import com.example.model.pojo.Messages;
import com.example.service.MessagesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessagesServiceImpl extends ServiceImpl<MessagesMapper, Messages> implements MessagesService {
    @Override
    public List<Messages> getMessages(Long senderId, Long receiverId) {
        QueryWrapper<Messages> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("sender_id", senderId)
                .eq("receiver_id", receiverId)
                .or(wrapper -> wrapper.eq("sender_id", receiverId).eq("receiver_id", senderId))
                .orderByAsc("timestamp"); // 假设你的时间字段是 "timestamp"
        List<Messages> messagesList = this.baseMapper.selectList(queryWrapper);
        return messagesList;
    }
}
