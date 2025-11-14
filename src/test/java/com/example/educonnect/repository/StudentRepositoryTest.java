package com.example.educonnect.repository;

import com.example.educonnect.TestcontainersConfiguration;
import com.example.educonnect.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
class StudentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void shouldSaveAndFindStudent() {
        Student student = new Student();
        student.setFullName("KoteTest");
        student.setEmail("kote.test@educonnect.com");
        student.setPhone("555123456");
        student.setStatus(true);
        student.setEnrollmentDate(Instant.now());

        Student savedStudent = entityManager.persistAndFlush(student);
        Student foundStudent = studentRepository.findById(savedStudent.getId()).orElse(null);

        assertThat(foundStudent).isNotNull();
        assertThat(foundStudent.getEmail()).isEqualTo(student.getEmail());
        assertThat(foundStudent.getFullName()).isEqualTo("KoteTest");
    }

    @Test
    void shouldReturnNullForInvalidId() {
        Long invalidId = 999L;

        Student foundStudent = studentRepository.findById(invalidId).orElse(null);

        assertThat(foundStudent).isNull();
    }
}