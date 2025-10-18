package com.example.educonnect.entity;

import com.example.educonnect.entity.common.Ownable;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
public class Payment implements Ownable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id" )
    private Student student;
    private BigDecimal amount;
    private boolean status;
    private Instant date;

    @Override
    public String getOwnerIdentifier() {
        return student.getEmail();
    }
}
