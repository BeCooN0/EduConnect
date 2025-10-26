package com.example.educonnect.controller;

import com.example.educonnect.dto.LessonRequestDto;
import com.example.educonnect.dto.LessonResponseDto;
import com.example.educonnect.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping("/api/lessons")
@RequiredArgsConstructor
@RestController
public class LessonController {
    private final LessonService lessonService;
    @PostMapping
    public ResponseEntity<LessonResponseDto> createLesson(@RequestBody LessonRequestDto lessonRequestDto){
        LessonResponseDto lessonResponseDto = lessonService.addLesson(lessonRequestDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/id")
                .buildAndExpand(lessonResponseDto.getId()).toUri();
        return ResponseEntity.created(location).body(lessonResponseDto);
    }
}

