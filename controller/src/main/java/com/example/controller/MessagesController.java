package com.example.controller;

import com.example.common.Result;
import com.example.model.pojo.Messages;
import com.example.service.MessagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/messages")
public class MessagesController {
    @Autowired
    private MessagesService messagesService;

    @PostMapping
    public Result createMessage(@RequestBody Messages message) {
        boolean flag = messagesService.save(message);
        if (flag == true){
            return Result.success("保存成功");
        }
        else {
            return Result.error("501","保存失败");
        }
    }

    @GetMapping
    public Result  getAllMessages(@RequestParam Long senderId,@RequestParam Long receiverId) {
        List<Messages> messages = messagesService.getMessages(senderId,receiverId);
        return Result.success(messages);
    }
}
