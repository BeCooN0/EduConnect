package com.example.educonnect.controller;

import com.example.educonnect.dto.StudentResponseDto;
import com.example.educonnect.security.JwtService;
import com.example.educonnect.service.CustomUserDetailsServiceImpl;
import com.example.educonnect.service.StudentService;
import com.example.educonnect.service.UserService;
import com.example.educonnect.service.security.AuthorizationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private StudentService studentService;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private CustomUserDetailsServiceImpl customUserDetailsService;
    @MockitoBean(name = "authService")
    private AuthorizationService authorizationService;

        @Test
        @WithMockUser()
        void shouldReturnStudentWhenStudentGetById() throws Exception {
            StudentResponseDto testStudent = new StudentResponseDto();
            testStudent.setId(1L);
            testStudent.setEmail("student@gmail.com");
            testStudent.setStatus(true);
            testStudent.setEnrollmentDate(Instant.now());
            testStudent.setPhone("00917381738");
            testStudent.setFullDame("fullName0081");


            when(studentService.getStudentById(1L)).thenReturn(testStudent);

            mockMvc.perform(get("/api/students/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email").value("student@gmail.com"))
                    .andExpect(jsonPath("$.id").value(1));
    }

}
