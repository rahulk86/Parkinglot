package com.parkinglot.modal;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class BikeParkingSpot extends ParkingSpot{

    public BikeParkingSpot(SpotDecorator decorator){
        super(decorator);
    }
    @Override
    public void scheduleMaintenance() {

    }

    @Override
    public double getArea() {
        return 0;
    }

}
