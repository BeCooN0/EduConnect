package com.example.educonnect.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
@Data
public class PaymentResponseDto {
    private Long id;
    private Long studentId;
    private BigDecimal amount;
    private boolean status;
    private Instant date;
}
