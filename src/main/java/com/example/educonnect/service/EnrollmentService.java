package com.example.educonnect.service;

import com.example.educonnect.dto.EnrollmentRequestDto;
import com.example.educonnect.dto.EnrollmentResponseDto;
import com.example.educonnect.dto.StatisticsResponseDto;
import com.example.educonnect.entity.Course;
import com.example.educonnect.entity.Enrollment;
import com.example.educonnect.entity.Student;
import com.example.educonnect.repository.CourseRepository;
import com.example.educonnect.repository.EnrollmentRepository;
import com.example.educonnect.repository.PaymentRepository;
import com.example.educonnect.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final PaymentRepository paymentRepository;

    public EnrollmentResponseDto addEnrollment(EnrollmentRequestDto enrollmentRequestDto) {
        Student student = studentRepository.findById(enrollmentRequestDto.getStudentId()).orElseThrow();
        Course course = courseRepository.findById(enrollmentRequestDto.getCourseId()).orElseThrow();
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        Enrollment saved = enrollmentRepository.save(enrollment);
        return modelMapper.map(saved, EnrollmentResponseDto.class);
    }

    public StatisticsResponseDto getAllStatistics() {
        StatisticsResponseDto dto = new StatisticsResponseDto();
        Long totalStudents = enrollmentRepository.getTotalStudents();
        Double totalAmount = paymentRepository.getTotalPaidAmount();
        List<Long> ids = enrollmentRepository.findMostPopularCourseId(PageRequest.of(0, 1));
        if (!ids.isEmpty()){
            Long courseId = ids.getFirst();
            courseRepository.findById(courseId).ifPresent(course -> dto.setMostPopularCourseTitle(course.getTitle()));
        } else {
            dto.setMostPopularCourseTitle("No enrollments yet");
            log.warn("No enrollments yet");
        }
        dto.setTotalStudents(totalStudents);
        dto.setTotalRevenue(BigDecimal.valueOf(totalAmount != null ? totalAmount : 0.0));
        return dto;
    }
}
