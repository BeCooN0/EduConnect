package com.example.educonnect.controller;

import com.example.educonnect.dto.PaymentResponseDto;
import com.example.educonnect.entity.enums.PaymentStatus;
import com.example.educonnect.repository.TenantRepository;
import com.example.educonnect.security.JwtService;
import com.example.educonnect.service.CustomUserDetailsServiceImpl;
import com.example.educonnect.service.PaymentService;
import com.example.educonnect.service.security.AuthorizationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService paymentService;
    @MockitoBean
    private JwtService jwtService;

    @MockitoBean(name = "authService")
    private AuthorizationService authorizationService;

    @MockitoBean
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @MockitoBean
    private TenantRepository tenantRepository;


    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnPaymentWhenGetPaymentById() throws Exception {

        PaymentResponseDto testPayment = new PaymentResponseDto();
        testPayment.setId(1L);
        testPayment.setAmount(BigDecimal.TEN);
        testPayment.setStatus(PaymentStatus.PAID);
        testPayment.setStudentId(1L);
        testPayment.setDate(Instant.now());

        when(paymentService.getPayment(1L)).thenReturn(testPayment);

        mockMvc.perform(get("/api/payments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.studentId").value(1L))
                .andExpect(jsonPath("$.status").value("PAID"));
    }
}