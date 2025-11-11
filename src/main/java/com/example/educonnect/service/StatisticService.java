package com.example.educonnect.service;

import com.example.educonnect.dto.StatisticsResponseDto;
import com.example.educonnect.repository.CourseRepository;
import com.example.educonnect.repository.EnrollmentRepository;
import com.example.educonnect.repository.PaymentRepository;
import com.example.educonnect.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticService {
    private final StudentRepository studentRepository;
    private final PaymentRepository paymentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StatisticsResponseDto getTenantStatistics() {
        StatisticsResponseDto dto = new StatisticsResponseDto();
        Long studentCount = studentRepository.count();
        dto.setTotalStudents(studentCount);

        Double totalPaidAmount = paymentRepository.getTotalPaidAmount(); //
        dto.setTotalRevenue(BigDecimal.valueOf(totalPaidAmount != null ? totalPaidAmount : 0.0));

        List<Long> popularCourseIds = enrollmentRepository.findMostPopularCourseId(PageRequest.of(0, 1));

        if (!popularCourseIds.isEmpty()) {
            Long courseId = popularCourseIds.getFirst();
            courseRepository.findById(courseId).ifPresent(course -> {
                dto.setMostPopularCourseTitle(course.getTitle());
            });
        } else {
            dto.setMostPopularCourseTitle("N/A");
            log.warn("No enrollments found to determine most popular course.");
        }

        return dto;
    }
}