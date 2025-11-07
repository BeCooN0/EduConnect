package com.example.educonnect.service;

import com.example.educonnect.dto.PaymentRequestDto;
import com.example.educonnect.dto.PaymentResponseDto;
import com.example.educonnect.entity.Payment;
import com.example.educonnect.entity.Student;
import com.example.educonnect.entity.enums.PaymentStatus;
import com.example.educonnect.mapper.PaymentMapper;
import com.example.educonnect.repository.PaymentRepository;
import com.example.educonnect.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    public PaymentResponseDto addPayment(PaymentRequestDto paymentRequestDto){
        Student student = studentRepository.findById(paymentRequestDto.getStudentId()).orElseThrow();
        Payment payment = paymentMapper.toPayment(paymentRequestDto);
        payment.setStudent(student);
        payment.setStatus(PaymentStatus.PAID);
        Payment saved = paymentRepository.save(payment);
        return paymentMapper.toDto(saved);
    }
}
