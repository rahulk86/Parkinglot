package com.parkinglot.modal;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Operator extends BaseModal{
    private String name;
}
