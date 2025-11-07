package com.example.educonnect.service;

import com.example.educonnect.dto.StudentRequestDto;
import com.example.educonnect.dto.StudentResponseDto;
import com.example.educonnect.entity.Student;
import com.example.educonnect.mapper.StudentMapper;
import com.example.educonnect.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    public StudentResponseDto addStudent(StudentRequestDto studentRequestDto){
        Student student = studentMapper.toStudent(studentRequestDto);
        Student saved = studentRepository.save(student);
        return studentMapper.toDto(saved);
    }
    public Page<StudentResponseDto> getAllStudents(Pageable pageable){
        Page<Student> students = studentRepository.findAll(pageable);
        return students.map(studentMapper::toDto);
    }
    public StudentResponseDto updateStudent(Long id ,StudentRequestDto studentRequestDto){
        Student student = studentRepository.findById(id).orElseThrow();
        studentMapper.updateStudentFromDto(studentRequestDto, student);
        Student saved = studentRepository.save(student);
        return studentMapper.toDto(saved);
    }

    public void deleteStudent(Long studentId){
        if (!studentRepository.existsById(studentId)){
            throw new EntityNotFoundException("Student with id " + studentId + " not found.");        }
        try{
            if (studentRepository.existsById(studentId)) {
                studentRepository.deleteById(studentId);
            }
        } catch (DataIntegrityViolationException e){
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}