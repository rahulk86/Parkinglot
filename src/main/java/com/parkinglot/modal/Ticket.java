package com.parkinglot.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Ticket {
    private boolean active;
    @Id
    private String id;

    private LocalDateTime entryTime;

    @ManyToOne
    private Customer customer;
    @ManyToOne
    private ParkingSpot parkingSpot;
    @ManyToOne
    private Operator generatedBy;
    @ManyToOne
    private Gate generatedAt;
}
