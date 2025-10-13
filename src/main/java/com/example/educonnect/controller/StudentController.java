package com.example.educonnect.controller;

import com.example.educonnect.dto.StudentRequestDto;
import com.example.educonnect.dto.StudentResponseDto;
import com.example.educonnect.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<StudentResponseDto> addStudent(@Validated @RequestBody StudentRequestDto studentRequestDto){
        StudentResponseDto studentResponseDto = studentService.addStudent(studentRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/id")
                .buildAndExpand(studentResponseDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(studentResponseDto);
    }
    @GetMapping
    public ResponseEntity<Page<StudentResponseDto>> getAllStudents(@PageableDefault(size = 10, page = 0) Pageable pageable){
        Page<StudentResponseDto> allStudents = studentService.getAllStudents(pageable);
        return ResponseEntity.ok(allStudents);
    }
}
