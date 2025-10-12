package com.example.educonnect.dto;

import com.example.educonnect.entity.Course;
import lombok.Data;

import java.time.Instant;

@Data
public class LessonResponseDto {
    private Long id;
    private Course course;
    private Instant date;
    private String topic;
    private Long duration;
    private String materialsUrl;
}