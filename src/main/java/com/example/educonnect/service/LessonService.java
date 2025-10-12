package com.example.educonnect.service;

import com.example.educonnect.dto.LessonRequestDto;
import com.example.educonnect.dto.LessonResponseDto;
import com.example.educonnect.entity.Lesson;
import com.example.educonnect.repository.CourseRepository;
import com.example.educonnect.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    public LessonResponseDto addLesson(LessonRequestDto lessonRequestDto){
        Lesson lesson = new Lesson();
        courseRepository.findById(lessonRequestDto.getCourseId()).ifPresent(lesson::setCourse);
        lesson.setDuration(lessonRequestDto.getDuration());
        lesson.setMaterialsUrl(lessonRequestDto.getMaterialsUrl());
        Lesson saved = lessonRepository.save(lesson);
        return modelMapper.map(saved, LessonResponseDto.class);

    }
}
