package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.pojo.Messages;

import java.util.List;

public interface MessagesService extends IService<Messages> {

    List<Messages> getMessages(Long senderId, Long receiverId);
}
