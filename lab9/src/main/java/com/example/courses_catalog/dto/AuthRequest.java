package com.example.courses_catalog.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
