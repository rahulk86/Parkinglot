package com.parkinglot.modal;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BusParkingSpot extends ParkingSpot{
    @Override
    public void scheduleMaintenance() {

    }

}
