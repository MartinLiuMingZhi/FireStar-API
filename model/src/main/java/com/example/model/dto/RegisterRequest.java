package com.example.model.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String checkPassword;
}
