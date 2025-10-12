package com.example.educonnect.dto;

import lombok.Data;

@Data
public class TeacherResponseDto {
    private Long id;
    private String fullName, email;
    private String phone, specialization;
}
