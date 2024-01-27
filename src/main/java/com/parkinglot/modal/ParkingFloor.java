package com.parkinglot.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class ParkingFloor extends BaseModal{
    private int floorNumber;
    @OneToMany(mappedBy = "floor")
    private List<ParkingSpot> parkingSpotsList;
    @ManyToOne
    private ParkingLot parkingLot;
}
