package com.example.educonnect.repository;

import com.example.educonnect.entity.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT s.student.id FROM Payment s WHERE s.status = 'PAID' GROUP BY s.student.id ORDER BY COUNT(s.id) DESC")
    List<Long> findTopStudentsByPaidPayments(Pageable pageable);
    @Query("SELECT sum (p.amount) from Payment p where p.status ='PAID'")
    Double getTotalPaidAmount();
}
