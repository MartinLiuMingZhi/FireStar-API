package com.example.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "comment")
public class Comment {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField(value = "post_id")
    private Long postId;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "content")
    private String content;

    @TableField("created_time")
    private LocalDateTime createdTime;
}
