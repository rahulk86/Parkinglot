package com.parkinglot.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SpotDecorator extends BaseModal{
    private String slotNumber;
    @Enumerated(EnumType.STRING)
    private ParkingSpotStatus status;
    public double getArea(){
        return 0;
    }

    public <T> T getParkingSpot(Class<T> aClass){
        return null;
    }
}
