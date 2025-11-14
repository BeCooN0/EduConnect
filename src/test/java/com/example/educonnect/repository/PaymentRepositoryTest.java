package com.example.educonnect.repository;

import com.example.educonnect.TestcontainersConfiguration;
import com.example.educonnect.entity.Payment;
import com.example.educonnect.entity.Student;
import com.example.educonnect.entity.enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldSaveAndFindPaymentById() {

        Student student = new Student();
        student.setEmail("student@gmail.com");
        student.setStatus(true);
        student.setFullName("student");
        student.setEnrollmentDate(Instant.now());
        Student savedStudent = entityManager.persistAndFlush(student);

        Payment payment = new Payment();
        payment.setDate(Instant.now());
        payment.setStudent(savedStudent);
        payment.setAmount(new BigDecimal("250.00"));
        payment.setStatus(PaymentStatus.PAID);


        Payment savedPayment = paymentRepository.save(payment);

        Optional<Payment> foundPaymentOpt = paymentRepository.findById(savedPayment.getId());


        assertThat(foundPaymentOpt).isPresent();

        assertThat(foundPaymentOpt.get().getStatus()).isEqualTo(PaymentStatus.PAID);

        assertThat(foundPaymentOpt.get().getAmount()).isEqualByComparingTo(new BigDecimal("250.00"));

        assertThat(foundPaymentOpt.get().getStudent().getId()).isEqualTo(savedStudent.getId());
    }
}