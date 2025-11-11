package com.example.educonnect.service;

import com.example.educonnect.dto.CourseRequestDto;
import com.example.educonnect.dto.CourseResponseDto;
import com.example.educonnect.entity.Course;
import com.example.educonnect.entity.Teacher;
import com.example.educonnect.mapper.CourseMapper;
import com.example.educonnect.repository.CourseRepository;
import com.example.educonnect.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final TeacherRepository teacherRepository;
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;
    public CourseResponseDto createCourse(CourseRequestDto courseRequestDto){
        Course course = courseMapper.toCourse(courseRequestDto);
        Teacher teacher = teacherRepository.findById(courseRequestDto.getTeacherId()).orElseThrow();
        course.setTeacher(teacher);
        Course saved = courseRepository.save(course);
        return courseMapper.toDto(saved);
    }
    public CourseResponseDto updateCourse(Long courseId, CourseRequestDto courseRequestDto){
        Course course = courseRepository.findById(courseId).orElseThrow();
        courseMapper.updateCourseFromDto(courseRequestDto, course);
//        Long currentTeacherId = course.getTeacher() != null ? course.getTeacher().getId() : null;
         Long currentTeacherId = Optional.ofNullable(course.getTeacher())
                 .map(Teacher::getId)
                 .orElse(null);
        Long newTeacherId = courseRequestDto.getTeacherId();
        if (newTeacherId != null && !Objects.equals(currentTeacherId, newTeacherId)){
            Teacher teacher1 = teacherRepository.findById(newTeacherId).orElseThrow(() -> new EntityNotFoundException("Teacher with id " + newTeacherId + " not found."));
            course.setTeacher(teacher1);
        }
        Course saved = courseRepository.save(course);
        return courseMapper.toDto(saved);
    }

    public void deleteCourse(Long courseId){
        if (!courseRepository.existsById(courseId)){
            throw new EntityNotFoundException("this courseId:" + courseId + " not found");
        }
        try {
            courseRepository.deleteById(courseId);
        } catch (Exception e) {
            throw new RuntimeException("Cannot delete course. It is referenced by other records.", e);
        }
    }
    public Page<CourseResponseDto> getAllCourses(Pageable pageable){
        Page<Course> allCourses = courseRepository.findAll(pageable);
        return allCourses.map(courseMapper::toDto);
    }

    public CourseResponseDto getCourse(Long courseId){
        Course course = courseRepository.findById(courseId).orElseThrow();
        return courseMapper.toDto(course);
    }
}
