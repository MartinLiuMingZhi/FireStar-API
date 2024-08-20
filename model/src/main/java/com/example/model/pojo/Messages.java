package com.example.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "messages")
public class Messages {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField(value = "sender_id")
    private Long senderId;

    @TableField(value = "receiver_id")
    private Long receiverId;

    @TableField(value = "content")
    private String content;

    @TableField(value = "timestamp")
    private LocalDateTime timestamp;

}
