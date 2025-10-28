package com.example.educonnect.repository;

import com.example.educonnect.entity.Enrollment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Query("SELECT e FROM Enrollment e GROUP BY e.course.id ORDER BY count(e.id) DESC")

    List<Long> findMostPopularCourseId(Pageable pageable);

    @Query("select count (s.student.id) from Enrollment s")
    Long getTotalStudents();
}
