package com.example.model.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long userid;

    private String username;

    private String email;

    private String avatar;

    private String sex;

    private Long status;
}
