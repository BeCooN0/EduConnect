package com.example.educonnect.service;

import com.example.educonnect.dto.TeacherRequestDto;
import com.example.educonnect.dto.TeacherResponseDto;
import com.example.educonnect.entity.Teacher;
import com.example.educonnect.mapper.TeacherMapper;
import com.example.educonnect.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {
    @Mock
    private TeacherMapper teacherMapper;
    @Mock
    private TeacherRepository teacherRepository;
    @InjectMocks
    private TeacherService teacherService;

    @Test
    void shouldAddTeacherSuccessfully(){
        TeacherRequestDto teacherRequestDto = new TeacherRequestDto();
        teacherRequestDto.setEmail("teacher@gmail.com");
        teacherRequestDto.setPhone("9951567382");
        teacherRequestDto.setFullName("teacher971");
        teacherRequestDto.setSpecialization("teacher");

        Teacher teacherEntity = new Teacher();
        teacherEntity.setId(1L);
        teacherEntity.setEmail(teacherRequestDto.getEmail());
        teacherEntity.setPhone(teacherRequestDto.getPhone());
        teacherEntity.setFullName(teacherRequestDto.getFullName());
        teacherEntity.setSpecialization(teacherEntity.getSpecialization());

        Teacher savedTeacher = new Teacher();
        savedTeacher.setSpecialization("math");
        savedTeacher.setEmail(teacherRequestDto.getEmail());
        savedTeacher.setPhone(teacherRequestDto.getPhone());
        savedTeacher.setFullName(teacherRequestDto.getFullName());
        teacherEntity.setId(1L);

        TeacherResponseDto teacherResponseDto = new TeacherResponseDto();
        teacherResponseDto.setFullName(teacherRequestDto.getFullName());
        teacherResponseDto.setId(1L);
        teacherResponseDto.setEmail(teacherRequestDto.getEmail());
        teacherResponseDto.setPhone(teacherRequestDto.getPhone());
        teacherResponseDto.setSpecialization(teacherRequestDto.getSpecialization());


        when(teacherMapper.toTeacherResponseDto(savedTeacher)).thenReturn(teacherResponseDto);
        when(teacherRepository.save(savedTeacher)).thenReturn(savedTeacher);
        when(teacherMapper.toTeacherResponseDto(savedTeacher)).thenReturn(teacherResponseDto);


        TeacherResponseDto result = teacherService.addTeacher(teacherRequestDto);
        assertThat(result).isNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFullName()).isEqualTo(teacherRequestDto.getFullName());

        verify(teacherRepository.save(savedTeacher));
    }
}
