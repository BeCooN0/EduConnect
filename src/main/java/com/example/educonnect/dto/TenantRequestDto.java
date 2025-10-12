package com.example.educonnect.dto;
import lombok.Data;

@Data
public class TenantRequestDto {
    private String name;
    private String subdomain;
    private boolean status;
}
