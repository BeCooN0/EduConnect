package com.example.educonnect.repository;

import com.example.educonnect.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends OwnableRepository<Course, Long> {
}
