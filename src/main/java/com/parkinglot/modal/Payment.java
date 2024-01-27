package com.parkinglot.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Payment extends BaseModal {
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;
    private LocalDateTime time;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private String referenceNumber;

    @ManyToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;
}
