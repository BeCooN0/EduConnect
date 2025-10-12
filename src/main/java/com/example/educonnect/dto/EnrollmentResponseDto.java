package com.example.educonnect.dto;

import lombok.Data;

import java.time.Instant;
@Data
public class EnrollmentResponseDto {
    private Long id;
    private Long studentId;
    private Long courseId;
    private Instant enrolledAt;
}
