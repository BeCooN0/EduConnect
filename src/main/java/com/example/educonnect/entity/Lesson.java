package com.example.educonnect.entity;

import com.example.educonnect.entity.common.Ownable;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class Lesson implements Ownable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    private Instant date;
    private String topic;
    private Long duration;
    private String materialsUrl;

    @Override
    public String getTenantId() {
        return "";
    }

    @Override
    public String getOwnerIdentifier() {
        return course.getTeacher().getEmail();
    }
}
