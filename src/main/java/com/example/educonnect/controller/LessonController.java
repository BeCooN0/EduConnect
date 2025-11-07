package com.example.educonnect.controller;

import com.example.educonnect.dto.LessonRequestDto;
import com.example.educonnect.dto.LessonResponseDto;
import com.example.educonnect.mapper.LessonMapper;
import com.example.educonnect.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping("/api/lessons")
@RequiredArgsConstructor
@RestController
public class LessonController {
    private final LessonMapper lessonMapper;
    private final LessonService lessonService;
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<LessonResponseDto> createLesson(@RequestBody LessonRequestDto lessonRequestDto){
        LessonResponseDto lessonResponseDto = lessonService.addLesson(lessonRequestDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/id")
                .buildAndExpand(lessonResponseDto.getId()).toUri();
        return ResponseEntity.created(location).body(lessonResponseDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or @authService.isOwner(principal, #id, T(com.example.educonnect.entity.Lesson))")
    public ResponseEntity<LessonResponseDto> updateLesson(@PathVariable Long id, @RequestBody LessonRequestDto lessonRequestDto){
        LessonResponseDto lessonResponseDto = lessonService.updateLesson(id, lessonRequestDto);
        return ResponseEntity.ok(lessonResponseDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or @authService.isOwner(principal, #id, T(com.example.educonnect.entity.Lesson))")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id){
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }


}

