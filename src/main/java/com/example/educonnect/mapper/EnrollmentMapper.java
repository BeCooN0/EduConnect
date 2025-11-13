package com.example.educonnect.mapper;

import com.example.educonnect.dto.EnrollmentRequestDto;
import com.example.educonnect.dto.EnrollmentResponseDto;
import com.example.educonnect.entity.Enrollment;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface EnrollmentMapper {
    EnrollmentResponseDto toDto(Enrollment enrollment);
    Enrollment toEnrollment(EnrollmentRequestDto enrollmentRequestDto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEnrollmentFromDto(EnrollmentRequestDto requestDto, @MappingTarget Enrollment enrollment);
}
