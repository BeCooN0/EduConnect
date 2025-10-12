package com.example.educonnect.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String email;
    private String passwordHash;
    private String fullName;
    private String tenantId;
}
