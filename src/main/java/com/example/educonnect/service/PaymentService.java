package com.example.educonnect.service;

import com.example.educonnect.dto.PaymentRequestDto;
import com.example.educonnect.dto.PaymentResponseDto;
import com.example.educonnect.entity.Payment;
import com.example.educonnect.entity.Student;
import com.example.educonnect.repository.PaymentRepository;
import com.example.educonnect.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public PaymentResponseDto addPayment(PaymentRequestDto paymentRequestDto){
        Student student = studentRepository.findById(paymentRequestDto.getStudentId()).orElseThrow();
        Payment payment = new Payment();
        payment.setAmount(paymentRequestDto.getAmount());
        payment.setStudent(student);
        payment.setStatus(true);
        Payment saved = paymentRepository.save(payment);
        return modelMapper.map(saved, PaymentResponseDto.class);
    }
}
