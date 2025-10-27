package com.example.educonnect.service;

import com.example.educonnect.dto.StatisticsResponseDto;
import com.example.educonnect.entity.enums.PaymentStatus;
import com.example.educonnect.repository.CourseRepository;
import com.example.educonnect.repository.EnrollmentRepository;
import com.example.educonnect.repository.PaymentRepository;
import com.example.educonnect.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final StudentRepository studentRepository;
    private final PaymentRepository paymentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StatisticsResponseDto getTenantStatistics() {
        StatisticsResponseDto responseDto = new StatisticsResponseDto();
        Long studentCount = studentRepository.count();
        BigDecimal totalRevenue = paymentRepository.sumAmountByStatus(PaymentStatus.PAID);
        responseDto.setTotalRevenue(totalRevenue);
        responseDto.setTotalStudents(studentCount);
        responseDto.setMostPopularCourseTitle("N/A");
    }
}
