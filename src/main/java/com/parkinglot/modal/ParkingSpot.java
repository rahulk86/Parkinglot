package com.parkinglot.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public abstract class ParkingSpot extends BaseModal{
    private String slotNumber;
    @Enumerated(EnumType.STRING)
    private ParkingSpotStatus status;
    @ManyToOne
    private ParkingFloor floor;
    public abstract void scheduleMaintenance();

}
