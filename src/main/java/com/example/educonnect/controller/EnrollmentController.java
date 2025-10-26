package com.example.educonnect.controller;

import com.example.educonnect.dto.EnrollmentRequestDto;
import com.example.educonnect.dto.EnrollmentResponseDto;
import com.example.educonnect.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/enrollments")
@RestController
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<EnrollmentResponseDto> addEnrollment(@RequestBody @Valid EnrollmentRequestDto enrollmentRequestDto){
        EnrollmentResponseDto enrollmentResponseDto = enrollmentService.addEnrollment(enrollmentRequestDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/id")
                .buildAndExpand(enrollmentResponseDto.getId())
                .toUri();
        
        return ResponseEntity.created(location).body(enrollmentResponseDto);
    }
}
