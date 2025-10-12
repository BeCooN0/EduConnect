package com.example.educonnect.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class CourseRequestDto {
    private String title;
    private String description;
    private Long teacherId;
    private Instant start_date;
    private Instant end_date;
    private boolean status;
}
