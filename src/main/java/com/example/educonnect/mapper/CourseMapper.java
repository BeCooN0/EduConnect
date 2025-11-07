package com.example.educonnect.mapper;

import com.example.educonnect.dto.CourseRequestDto;
import com.example.educonnect.dto.CourseResponseDto;
import com.example.educonnect.entity.Course;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseResponseDto toDto(Course course);
    Course toCourse(CourseRequestDto courseRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCourseFromDto(CourseRequestDto courseRequestDto, @MappingTarget Course course);

}
