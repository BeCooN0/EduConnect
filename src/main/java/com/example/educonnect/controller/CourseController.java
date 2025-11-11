package com.example.educonnect.controller;

import com.example.educonnect.dto.CourseRequestDto;
import com.example.educonnect.dto.CourseResponseDto;
import com.example.educonnect.service.CourseService;
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
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping
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
    @PreAuthorize("hasRole('ADMIN') or @authService.isOwner(principal, #courseId, T(com.example.educonnect.entity.Course))")
    public ResponseEntity<CourseResponseDto> updateCourse(@PathVariable Long courseId, @RequestBody CourseRequestDto courseRequestDto){
        CourseResponseDto courseResponseDto = courseService.updateCourse(courseId, courseRequestDto);
        return ResponseEntity.ok(courseResponseDto);
    }
    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasRole('ADMIN') or @authService.isOwner(principal, #courseId, T(com.example.educonnect.entity.Course))")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId){
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<Page<CourseResponseDto>> getAllCourses(@PageableDefault Pageable pageable){
        Page<CourseResponseDto> allCourses = courseService.getAllCourses(pageable);
        return ResponseEntity.ok(allCourses);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourse(@PathVariable Long id){
        CourseResponseDto course = courseService.getCourse(id);
        return ResponseEntity.ok(course);
    }
}
