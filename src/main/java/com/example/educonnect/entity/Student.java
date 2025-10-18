package com.example.educonnect.entity;

import com.example.educonnect.entity.common.Ownable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class Student implements Ownable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName, email, phone;
    private Instant enrollmentDate;
    private boolean status;

    @Override
    public String getOwnerIdentifier() {
        return this.email;
    }
}
