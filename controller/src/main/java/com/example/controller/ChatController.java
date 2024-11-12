package com.example.controller;

import com.example.model.pojo.Messages;
import com.example.model.dto.MessageDTO;
import com.example.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;

@CrossOrigin
@Controller
public class ChatController {

    @Autowired
    private MessagesService messagesService;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Messages send(@Payload MessageDTO messageDTO) throws Exception {
        Messages message = new Messages();
        message.setSenderId(messageDTO.getSenderId());
        message.setReceiverId(messageDTO.getReceiverId());
        message.setContent(messageDTO.getContent());
        message.setTimestamp(LocalDateTime.now());

        messagesService.save(message);
        return message;

    }



}