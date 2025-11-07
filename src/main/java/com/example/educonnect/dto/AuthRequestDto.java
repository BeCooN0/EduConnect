package com.example.educonnect.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class AuthRequestDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String tenantId;
}
