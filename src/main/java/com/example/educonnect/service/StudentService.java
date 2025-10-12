package com.example.educonnect.service;

import com.example.educonnect.dto.StudentRequestDto;
import com.example.educonnect.dto.StudentResponseDto;
import com.example.educonnect.entity.Student;
import com.example.educonnect.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    public StudentResponseDto addStudent(StudentRequestDto studentRequestDto){
        Student student = modelMapper.map(studentRequestDto, Student.class);
        Student saved = studentRepository.save(student);
        return modelMapper.map(saved, StudentResponseDto.class);
    }
}