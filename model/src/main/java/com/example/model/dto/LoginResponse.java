package com.example.model.dto;

import lombok.Data;

@Data
public class LoginResponse {

    private Long userid;

    private String username;

    private String email;

    private String avatar;

    private String sex;
}
