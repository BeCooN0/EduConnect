package com.example.educonnect.service;

import com.example.educonnect.dto.LessonRequestDto;
import com.example.educonnect.dto.LessonResponseDto;
import com.example.educonnect.entity.Course;
import com.example.educonnect.entity.Lesson;
import com.example.educonnect.mapper.LessonMapper;
import com.example.educonnect.repository.CourseRepository;
import com.example.educonnect.repository.LessonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final LessonMapper lessonMapper;
    public LessonResponseDto addLesson(LessonRequestDto lessonRequestDto){
        Lesson lesson = lessonMapper.toLesson(lessonRequestDto);
        courseRepository.findById(lessonRequestDto.getCourseId()).ifPresent(lesson::setCourse);
        Lesson saved = lessonRepository.save(lesson);
        return lessonMapper.toLessonDto(saved);
    }

    public void deleteLesson(Long id){
        if (!lessonRepository.existsById(id)){
            throw new EntityNotFoundException("lesson with id :" + id  + " " + "not found!");
        }
        try {
            lessonRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(e);
        }
    }

    public LessonResponseDto updateLesson(Long lessonId, LessonRequestDto lessonRequestDto){
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(()-> new EntityNotFoundException("this lessonId not found!"));
        lessonMapper.updateLessonFromDto(lessonRequestDto, lesson);
        if (lesson.getCourse() != null && !Objects.equals(lesson.getCourse().getId(), lessonRequestDto.getCourseId())){
            Course course = courseRepository.findById(lessonRequestDto.getCourseId()).orElseThrow();
            lesson.setCourse(course);
        }
        Lesson save = lessonRepository.save(lesson);
        return lessonMapper.toLessonDto(save);
    }

}
