package com.example.educonnect.controller;

import com.example.educonnect.dto.TeacherResponseDto;
import com.example.educonnect.security.JwtService;
import com.example.educonnect.service.CustomUserDetailsServiceImpl;
import com.example.educonnect.service.TeacherService;
import com.example.educonnect.service.UserService;
import com.example.educonnect.service.security.AuthorizationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TeacherController.class)
public class TeacherControllerTest {
    @MockitoBean
    private TeacherService teacherService;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean(name = "authService")
    private AuthorizationService authorizationService;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Test
    @WithMockUser
    void shouldReturnTeacherWhenGetTeacherById() throws Exception {
        TeacherResponseDto teacherResponseDto = new TeacherResponseDto();
        teacherResponseDto.setId(1L);
        teacherResponseDto.setEmail("teacher@email.com");
        teacherResponseDto.setPhone("08279724991");
        teacherResponseDto.setSpecialization("teacher");
        teacherResponseDto.setFullName("nikolas_Smith");

        when(teacherService.getTeacherById(1L)).thenReturn(teacherResponseDto);

        mockMvc.perform(get("api/teachers/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("teacher@email.com"))
                .andExpect(jsonPath("$.fullName").value("nikolas_Smith"));
    }
}