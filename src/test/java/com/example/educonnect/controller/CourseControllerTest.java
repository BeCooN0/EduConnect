package com.example.educonnect.controller;

import com.example.educonnect.dto.CourseResponseDto;

import com.example.educonnect.repository.TenantRepository;
import com.example.educonnect.security.JwtService;
import com.example.educonnect.service.*;
import com.example.educonnect.service.security.AuthorizationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.Instant;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CourseService courseService;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private CustomUserDetailsServiceImpl customUserDetailsService;
    @MockitoBean
    private TenantRepository tenantRepository;
    @MockitoBean(name = "authService")
    private AuthorizationService authorizationService;


    @Test
    @WithMockUser(roles = "ADMIN")
    void ShouldReturnCourseWhenGetCourseById() throws Exception {
        CourseResponseDto courseResponseDto = new CourseResponseDto();
        courseResponseDto.setId(1L);
        courseResponseDto.setDescription("desc");
        courseResponseDto.setStatus(true);
        courseResponseDto.setTitle("title");
        courseResponseDto.setEndDate(Instant.EPOCH);
        courseResponseDto.setStartDate(Instant.now());
        courseResponseDto.setTeacherId(1L);


        when(courseService.getCourse(1L)).thenReturn(courseResponseDto);

        mockMvc.perform(get("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.teacherId").value(1));
    }
}
