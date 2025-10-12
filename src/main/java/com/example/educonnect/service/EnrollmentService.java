package com.example.educonnect.service;

import com.example.educonnect.dto.EnrollmentRequestDto;
import com.example.educonnect.dto.EnrollmentResponseDto;
import com.example.educonnect.entity.Course;
import com.example.educonnect.entity.Enrollment;
import com.example.educonnect.entity.Student;
import com.example.educonnect.repository.CourseRepository;
import com.example.educonnect.repository.EnrollmentRepository;
import com.example.educonnect.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    public EnrollmentResponseDto addEnrollment(EnrollmentRequestDto enrollmentRequestDto){
        Student student = studentRepository.findById(enrollmentRequestDto.getStudentId()).orElseThrow();
        Course course = courseRepository.findById(enrollmentRequestDto.getCourseId()).orElseThrow();
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        Enrollment saved = enrollmentRepository.save(enrollment);
        return modelMapper.map(saved, EnrollmentResponseDto.class);
    }

}
