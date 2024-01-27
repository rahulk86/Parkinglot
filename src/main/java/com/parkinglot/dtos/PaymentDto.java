package com.parkinglot.dtos;

import com.parkinglot.modal.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentDto {
    private String paymentMode;
    private Double amount;
    private PaymentStatus status;
    private String referenceNumber;
    private String invoiceNumber;
}
