package com.example.educonnect.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class StudentRequestDto {
    private String fullName, email, phone;
    private Instant enrollmentDate;
    private boolean status;
}
