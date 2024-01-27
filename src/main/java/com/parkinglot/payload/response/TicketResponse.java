package com.parkinglot.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TicketResponse {
    private String id;
    private String name;
    private String slotNumber;
    private String operatorName;
    private String gateNumber;
}
