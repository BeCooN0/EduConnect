package com.example.educonnect.service;

import com.example.educonnect.dto.PaymentRequestDto;
import com.example.educonnect.dto.PaymentResponseDto;
import com.example.educonnect.entity.Payment;
import com.example.educonnect.entity.Student;
import com.example.educonnect.entity.enums.PaymentStatus;
import com.example.educonnect.mapper.PaymentMapper;
import com.example.educonnect.repository.PaymentRepository;
import com.example.educonnect.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public PaymentResponseDto updatePayment(Long id, PaymentRequestDto paymentRequestDto){
        Payment payment = paymentRepository.findById(id).orElseThrow();
        paymentMapper.updatePaymentFromDto(paymentRequestDto, payment);
        Payment saved = paymentRepository.save(payment);
        return paymentMapper.toDto(saved);
    }
    public void deletePayment(Long paymentId){
        paymentRepository.deleteById(paymentId);
    }
    public Page<PaymentResponseDto> getAllPayments(Pageable pageable){
        Page<Payment> all = paymentRepository.findAll(pageable);
        return all.map(paymentMapper::toDto);
    }
    public PaymentResponseDto getPayment(Long paymentId){
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() ->
                new EntityNotFoundException("this paymentId was not found!"));
        return paymentMapper.toDto(payment);
    }
}
