package com.example.educonnect.dto;

import lombok.Data;

@Data
public class TeacherRequestDto {
    private String fullName, email;
    private String phone, specialization;
}
