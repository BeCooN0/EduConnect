package com.example.educonnect.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class PaymentResponseDto {
    private Long id;
    private Long studentId;
    private BigDecimal amount;
    private boolean status;
    private Instant date;
}
