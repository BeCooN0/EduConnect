package com.example.educonnect.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class CourseResponseDto {
    private Long id;
    private String title;
    private String description;
    private Long teacherId;
    private Instant startDate;
    private Instant endDate;
    private boolean status;
}
