package com.example.educonnect.service;

import com.example.educonnect.dto.StudentRequestDto;
import com.example.educonnect.dto.StudentResponseDto;
import com.example.educonnect.entity.Student;
import com.example.educonnect.entity.User;
import com.example.educonnect.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<StudentResponseDto> getAllStudents(Pageable pageable){
        Page<Student> students = studentRepository.findAll(pageable);
        return students.map(student -> modelMapper.map(student, StudentResponseDto.class));
    }
    public StudentResponseDto updateStudent(Long id ,StudentRequestDto studentRequestDto){
        Student student = studentRepository.findById(id).orElseThrow();
        modelMapper.map(studentRequestDto, student);
        Student saved = studentRepository.save(student);
        return modelMapper.map(saved, StudentResponseDto.class);
    }

}