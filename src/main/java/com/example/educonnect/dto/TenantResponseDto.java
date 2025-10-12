package com.example.educonnect.dto;

import com.example.educonnect.entity.enums.PlanType;
import lombok.Data;
import java.time.Instant;
@Data
public class TenantResponseDto {
    private Long id;
    private String name;
    private String subdomain;
    private Instant createdAt;
    private boolean status;
    private PlanType plan;

}
