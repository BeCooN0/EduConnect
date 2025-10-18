package com.example.educonnect.service;

import com.example.educonnect.dto.CourseRequestDto;
import com.example.educonnect.dto.CourseResponseDto;
import com.example.educonnect.entity.Course;
import com.example.educonnect.entity.Teacher;
import com.example.educonnect.repository.CourseRepository;
import com.example.educonnect.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;
    public CourseResponseDto createCourse(CourseRequestDto courseRequestDto){
        Course course = modelMapper.map(courseRequestDto, Course.class);
        Teacher teacher = teacherRepository.findById(courseRequestDto.getTeacherId()).orElseThrow();
        course.setTeacher(teacher);
        Course saved = courseRepository.save(course);
        return modelMapper.map(saved, CourseResponseDto.class);
    }
    public CourseResponseDto updateCourse(Long courseId, CourseRequestDto courseRequestDto){
        Course course = courseRepository.findById(courseId).orElseThrow();
        modelMapper.map(courseRequestDto, course);
        Course saved = courseRepository.save(course);
        return modelMapper.map(saved, CourseResponseDto.class);
    }
}
