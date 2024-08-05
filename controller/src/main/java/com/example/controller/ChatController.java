package com.example.controller;

import com.example.model.pojo.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message send(Message message) throws Exception {
        // 模拟处理延迟
        Thread.sleep(1000);
        return message;
//        return new Message("刘明智","Hello");
    }

    // 省略其他代码
//    @MessageMapping("/receive")
//    @SendTo("/topic/messages")
//    public Message receiveMessage(Message message) {
//        return message;
//    }

}