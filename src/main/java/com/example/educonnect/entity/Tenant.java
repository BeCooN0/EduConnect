package com.example.educonnect.entity;

import com.example.educonnect.entity.enums.PlanType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String subdomain;
    private Instant created_at;
    private boolean status;
    @Enumerated(EnumType.STRING)
    private PlanType plan;

}
