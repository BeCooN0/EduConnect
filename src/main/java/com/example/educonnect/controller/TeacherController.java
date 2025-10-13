package com.example.educonnect.controller;

import com.example.educonnect.dto.TeacherRequestDto;
import com.example.educonnect.dto.TeacherResponseDto;
import com.example.educonnect.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    @PostMapping
    public ResponseEntity<TeacherResponseDto> addTeacher(@RequestBody TeacherRequestDto teacherRequestDto){
        TeacherResponseDto teacherResponseDto = teacherService.addTeacher(teacherRequestDto);
        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/id")
                        .buildAndExpand(teacherResponseDto.getId())
                        .toUri();
        return ResponseEntity.created(location).body(teacherResponseDto);
    }
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<TeacherResponseDto>> getAllTeacher(@PageableDefault(size = 10, page = 0) Pageable pageable){
        Page<TeacherResponseDto> allTeacher = teacherService.getAllTeacher(pageable);
        return ResponseEntity.ok(allTeacher);
    }
}
