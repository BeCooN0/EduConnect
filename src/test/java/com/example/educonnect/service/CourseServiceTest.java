package com.example.educonnect.service;

import com.example.educonnect.dto.CourseRequestDto;
import com.example.educonnect.dto.CourseResponseDto;
import com.example.educonnect.entity.Course;
import com.example.educonnect.entity.Teacher;
import com.example.educonnect.mapper.CourseMapper;
import com.example.educonnect.repository.CourseRepository;
import com.example.educonnect.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @Mock
    private CourseMapper courseMapper;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private CourseService courseService;
    @Test
    public void shouldAddCourseSuccessfully(){
        CourseRequestDto courseRequestDto = new CourseRequestDto();
        courseRequestDto.setTitle("java_course");
        courseRequestDto.setDescription("this is best course in the world!");
        courseRequestDto.setTeacherId(1L);
        courseRequestDto.setStatus(true);
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        Course course = new Course();
        course.setDescription(courseRequestDto.getDescription());
        course.setEndDate(courseRequestDto.getEnd_date());
        course.setStartDate(courseRequestDto.getStart_date());
        course.setTitle(courseRequestDto.getTitle());
        course.setTeacher(teacher);



        Course savedCourse = new Course();
        savedCourse.setTitle(courseRequestDto.getTitle());
        savedCourse.setId(1L);
        savedCourse.setDescription(courseRequestDto.getDescription());
        savedCourse.setStatus(true);
        savedCourse.setEndDate(courseRequestDto.getEnd_date());
        savedCourse.setStartDate(courseRequestDto.getStart_date());
        savedCourse.setTeacher(teacher);


        CourseResponseDto response = new CourseResponseDto();
        response.setTeacherId(savedCourse.getId());
        response.setId(savedCourse.getId());
        response.setStatus(true);
        response.setDescription(savedCourse.getDescription());
        response.setTitle(savedCourse.getTitle());
        response.setStartDate(savedCourse.getStartDate());
        response.setEndDate(savedCourse.getEndDate());

        when(courseMapper.toCourse(courseRequestDto)).thenReturn(course);
        when(courseMapper.toDto(savedCourse)).thenReturn(response);
        when(courseRepository.save(course)).thenReturn(savedCourse);
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        CourseResponseDto result = courseService.createCourse(courseRequestDto);


        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("java_course");
        verify(courseRepository, times(1)).save(course);
    }

}
