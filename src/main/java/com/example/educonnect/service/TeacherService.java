package com.example.educonnect.service;

import com.example.educonnect.dto.TeacherRequestDto;
import com.example.educonnect.dto.TeacherResponseDto;
import com.example.educonnect.entity.Teacher;
import com.example.educonnect.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final ModelMapper modelMapper;
    private final TeacherRepository teacherRepository;
    public TeacherResponseDto addTeacher(TeacherRequestDto teacherRequestDto){
        Teacher teacher = modelMapper.map(teacherRequestDto, Teacher.class);
        Teacher saved = teacherRepository.save(teacher);
        return modelMapper.map(saved, TeacherResponseDto.class);
    }

    public Page<TeacherResponseDto> getAllTeacher(Pageable pageable){
        Page<Teacher> all = teacherRepository.findAll(pageable);
        return all.map(teacher -> modelMapper.map(teacher, TeacherResponseDto.class));
    }
    public TeacherResponseDto updateTeacher(Long teacherId, TeacherRequestDto teacherRequestDto){
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow();
        modelMapper.map(teacherRequestDto, teacher);
        Teacher saved = teacherRepository.save(teacher);
        return modelMapper.map(saved, TeacherResponseDto.class);
    }
}
