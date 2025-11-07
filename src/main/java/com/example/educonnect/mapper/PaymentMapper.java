package com.example.educonnect.mapper;

import com.example.educonnect.dto.PaymentRequestDto;
import com.example.educonnect.dto.PaymentResponseDto;
import com.example.educonnect.entity.Payment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentResponseDto toDto(Payment payment);
    Payment toPayment(PaymentRequestDto paymentRequestDto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePaymentFromDto(PaymentRequestDto paymentRequestDto, @MappingTarget Payment payment);
}
