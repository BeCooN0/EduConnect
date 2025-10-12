package com.example.educonnect.dto;

import com.example.educonnect.entity.enums.UserRole;
import lombok.Data;

import java.time.Instant;
@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String fullName;
    private UserRole role;
    private Instant createdAt;
    private boolean isActive;
    private String tenantId;
}