package com.example.educonnect.repository;

import com.example.educonnect.entity.Course;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends OwnableRepository<Course, Long> {
    @Query("""
    SELECT t 
    FROM Course t 
    WHERE LOWER(t.title) like  concat ('%', :query, '%') or LOWER(t.teacher.fullName) like  concat ('%', :query, '%')
""")
    Page<Course> searchByTitleOrTeacher(@Param("query") String query, Pageable pageable);
}
