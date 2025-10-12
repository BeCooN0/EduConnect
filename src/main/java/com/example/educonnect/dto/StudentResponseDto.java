package com.example.educonnect.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class StudentResponseDto {
    private Long id;
    private String fullDame, email, phone;
    private Instant enrollmentDate;
    private boolean status;
}
