package com.parkinglot.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Invoice{
    private boolean active;
    @Id
    private String id;
    private LocalDateTime exitTime;

    @OneToMany(mappedBy = "invoice")
    private List<Payment> payment;
    @OneToOne
    private Ticket ticket;
    @ManyToOne
    private Gate generatedAt;
    @ManyToOne
    private Operator generatedBy;
    private double fee;
}
