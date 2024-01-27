package com.parkinglot.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotConfirmationRequest {
    private String ticketId;
    private String spotId;
}
