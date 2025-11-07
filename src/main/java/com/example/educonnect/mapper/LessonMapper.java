package com.example.educonnect.mapper;

import com.example.educonnect.dto.LessonRequestDto;
import com.example.educonnect.dto.LessonResponseDto;
import com.example.educonnect.entity.Lesson;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    LessonResponseDto toLessonDto(Lesson lesson);
    Lesson toLesson(LessonRequestDto lessonRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLessonFromDto(LessonRequestDto lessonRequestDto, @MappingTarget Lesson lesson);
}
