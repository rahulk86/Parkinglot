package com.parkinglot.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceDto {
    private String ticketId;
    private Long  operatorId;
    private String  gateNumber;
}
