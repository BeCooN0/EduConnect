package com.example.educonnect.entity;

import com.example.educonnect.entity.common.Ownable;
import com.example.educonnect.entity.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class User implements Ownable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String passwordHash;
    private String fullName;
    private UserRole role;
    private Instant createdAt;
    private boolean isActive;

    @Override
    public String getOwnerIdentifier() {
        return this.email;
    }
}
