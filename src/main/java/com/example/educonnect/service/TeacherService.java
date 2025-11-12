package com.example.educonnect.service;

import com.example.educonnect.dto.TeacherRequestDto;
import com.example.educonnect.dto.TeacherResponseDto;
import com.example.educonnect.entity.Teacher;
import com.example.educonnect.mapper.TeacherMapper; // <-- იმპორტი
import com.example.educonnect.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherMapper teacherMapper;
    private final TeacherRepository teacherRepository;

    public TeacherResponseDto addTeacher(TeacherRequestDto teacherRequestDto){
        Teacher teacher = teacherMapper.toTeacher(teacherRequestDto);
        Teacher saved = teacherRepository.save(teacher);
        return teacherMapper.toTeacherResponseDto(saved);
    }

    public Page<TeacherResponseDto> getAllTeacher(Pageable pageable){
        Page<Teacher> all = teacherRepository.findAll(pageable);
        return all.map(teacherMapper::toTeacherResponseDto);
    }

    public TeacherResponseDto updateTeacher(Long teacherId, TeacherRequestDto teacherRequestDto){
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow();
        teacherMapper.updateTeacherFromDto(teacherRequestDto, teacher);
        Teacher saved = teacherRepository.save(teacher);
        return teacherMapper.toTeacherResponseDto(saved);
    }
    public void deleteTeacher(Long id){
        if(!teacherRepository.existsById(id)){
            throw new EntityNotFoundException();
        }
        try {
            teacherRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new RuntimeException(e);
        }
    }
    public TeacherResponseDto getTeacherById(Long id){
        Teacher teacher = teacherRepository.findById(id).orElseThrow(EntityNotFoundException::new);
       return teacherMapper.toTeacherResponseDto(teacher);
    }
}