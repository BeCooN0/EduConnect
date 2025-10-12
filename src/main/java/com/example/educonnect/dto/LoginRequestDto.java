package com.example.educonnect.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String passwordHash;
    private String fullName;
    private String tenantId;
}
