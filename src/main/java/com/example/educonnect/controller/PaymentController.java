package com.example.educonnect.controller;

import com.example.educonnect.dto.PaymentRequestDto;
import com.example.educonnect.dto.PaymentResponseDto;
import com.example.educonnect.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping
    public ResponseEntity<PaymentResponseDto> addPayment(@RequestBody PaymentRequestDto paymentRequestDto){
        PaymentResponseDto paymentResponseDto = paymentService.addPayment(paymentRequestDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/id")
                .buildAndExpand(paymentResponseDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(paymentResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> updatePayment(@PathVariable Long id, @RequestBody PaymentRequestDto paymentRequestDto){
        PaymentResponseDto paymentResponseDto = paymentService.updatePayment(id, paymentRequestDto);
        return ResponseEntity.ok(paymentResponseDto);
    }
    @GetMapping
    public ResponseEntity<Page<PaymentResponseDto>> getAllPayments(@PageableDefault(size = 10, page = 0) Pageable pageable){
        Page<PaymentResponseDto> allPayments = paymentService.getAllPayments(pageable);
        return ResponseEntity.ok(allPayments);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authService.isOwner(principal, #id, T(com.example.educonnect.entity.Payment))")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id){
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authService.isOwner(principal, #id, T(com.example.educonnect.entity.Payment))")
    public ResponseEntity<PaymentResponseDto> getPayment(@PathVariable Long id){
        PaymentResponseDto payment = paymentService.getPayment(id);
        return ResponseEntity.ok(payment);
    }
}
