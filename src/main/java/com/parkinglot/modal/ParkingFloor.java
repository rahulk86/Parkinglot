package com.parkinglot.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ParkingFloor extends BaseModal{
    private int floorNumber;
    @OneToOne
    private SpotDecorator spotDecorator;
    @ManyToOne
    private ParkingLot parkingLot;
}
