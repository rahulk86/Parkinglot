package com.parkinglot.modal;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Gate extends BaseModal{
    private String gateNumber;
    @ManyToOne
    private Operator currentOperator;
    @ManyToOne
    private ParkingLot parkingLot;
    @Enumerated(EnumType.STRING)
    private GateType gateType;
}