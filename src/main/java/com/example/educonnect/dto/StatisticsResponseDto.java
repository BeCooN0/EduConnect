package com.example.educonnect.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class StatisticsResponseDto {
    private Long totalStudents;
    private BigDecimal totalRevenue;
    private String mostPopularCourseTitle;
}
