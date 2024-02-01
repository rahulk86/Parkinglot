package com.parkinglot.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public abstract class ParkingSpot extends SpotDecorator implements Cloneable{
    @ManyToOne
    private  SpotDecorator   decorator;
    public ParkingSpot(SpotDecorator decorator){
        this.decorator = decorator;
    }
    public abstract void scheduleMaintenance();
    public abstract double getArea();

    @Override
    public ParkingSpot clone() {
        try {
            return (ParkingSpot) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    public <T> T getParkingSpot(Class<T> aClass) {
        if(aClass.isInstance(decorator) &&  decorator.getStatus().equals(ParkingSpotStatus.AVAILABLE)){
            return aClass.cast(decorator);
        }
        return decorator.getParkingSpot(aClass);
    }
}
