package com.parkinglot.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Vehicle extends BaseModal{
    private String vehicleNumber;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
}
