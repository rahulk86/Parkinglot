package com.parkinglot.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InvoiceResponse {
    private String id;
    private Double fee;
    private String operatorName;
}
