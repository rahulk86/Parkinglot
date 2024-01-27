package com.parkinglot.dtos;

import com.parkinglot.modal.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDto {
    private String id;
    private String name;
    private String licenceNumber;
    private String vehicleNumber;
    private VehicleType vehicleType;
    private Long  operatorId;
    private Long  gateId;
}
