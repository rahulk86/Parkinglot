package com.parkinglot.modal;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BikeParkingSpot extends ParkingSpot{
    @Override
    public void scheduleMaintenance() {

    }
}
