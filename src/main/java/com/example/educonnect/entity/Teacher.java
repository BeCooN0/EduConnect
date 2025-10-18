package com.example.educonnect.entity;

import com.example.educonnect.entity.common.Ownable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Teacher implements Ownable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName, email;
    private String phone, specialization;

    @Override
    public String getOwnerIdentifier() {
        return this.email;
    }
}
