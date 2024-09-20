package com.example.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "post")
public class Post {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("user_id")  // 指定映射的字段名
    private Long userId;

    @TableField("content")
    private String content;

    @TableField("created_time")
    private LocalDateTime createdTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    // Getters and Setters
}
