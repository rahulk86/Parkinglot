package com.parkinglot.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class ParkingLot extends BaseModal{
    private String name;
    private String address;
    @OneToMany(mappedBy = "parkingLot")
    private List<ParkingFloor> floors;
    @OneToMany(mappedBy = "parkingLot")
    private List<Gate> entry;
    @OneToMany(mappedBy = "parkingLot")
    private List<Gate> exit;
}
