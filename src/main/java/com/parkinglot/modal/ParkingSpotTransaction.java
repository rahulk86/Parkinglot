package com.parkinglot.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ParkingSpotTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Ticket ticket;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
}
