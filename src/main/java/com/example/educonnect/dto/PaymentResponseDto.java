package com.example.educonnect.dto;

import com.example.educonnect.entity.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
@Data
public class PaymentResponseDto {
    private Long id;
    private Long studentId;
    private BigDecimal amount;
    private PaymentStatus status;
    private Instant date;
}
