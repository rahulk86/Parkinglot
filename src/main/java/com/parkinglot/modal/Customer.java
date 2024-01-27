package com.parkinglot.modal;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Customer extends BaseModal{
    private String name;
    @ManyToOne
    private Vehicle vehicle;
    private String licenceNumber;
}
