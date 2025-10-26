package com.example.educonnect.controller;

import com.example.educonnect.dto.CourseRequestDto;
import com.example.educonnect.dto.CourseResponseDto;
import com.example.educonnect.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody CourseRequestDto courseRequestDto){
        CourseResponseDto course = courseService.createCourse(courseRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/id")
                .buildAndExpand(course.getId())
                .toUri();

        return ResponseEntity.created(location).body(course);
    }
    @PutMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or @authService.isOwner(principal, #courseId, T(com.example.educonnect.entity.Course))")
    public ResponseEntity<CourseResponseDto> updateCourse(@PathVariable Long courseId, @RequestBody CourseRequestDto courseRequestDto){
        CourseResponseDto courseResponseDto = courseService.updateCourse(courseId, courseRequestDto);
        return ResponseEntity.ok(courseResponseDto);
    }
}
