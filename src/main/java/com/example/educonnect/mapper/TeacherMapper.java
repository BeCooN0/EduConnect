package com.example.educonnect.mapper;

import com.example.educonnect.dto.TeacherRequestDto;
import com.example.educonnect.dto.TeacherResponseDto;
import com.example.educonnect.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherResponseDto toTeacherResponseDto(Teacher teacher);
    Teacher toTeacher(TeacherRequestDto teacherRequestDto);
    void updateTeacherFromDto(TeacherRequestDto teacherRequestDto, @MappingTarget Teacher teacher);
}