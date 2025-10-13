package com.example.educonnect.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TeacherRequestDto {
    private String fullName;
    @Size(min = 2, max = 100)
    private String email;
    private String phone;
    private String specialization;
}
