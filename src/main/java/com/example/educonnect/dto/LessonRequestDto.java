package com.example.educonnect.dto;

import lombok.Data;
@Data
public class LessonRequestDto {
    private Long courseId;
    private String topic;
    private Long duration;
    private String materialsUrl;
}
