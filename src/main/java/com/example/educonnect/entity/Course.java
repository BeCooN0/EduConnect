package com.example.educonnect.entity;

import com.example.educonnect.entity.common.Ownable;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class Course implements Ownable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title, description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    private Instant startDate, endDate;
    private boolean status;

    @Override
    public String getTenantId() {
        return "";
    }

    @Override
    public String getOwnerIdentifier() {
        return teacher.getEmail() != null ? teacher.getEmail() : null;
    }
}
