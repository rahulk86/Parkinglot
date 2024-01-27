package com.parkinglot.controller;

import com.parkinglot.dtos.TicketDto;
import com.parkinglot.exceptions.TicketException;
import com.parkinglot.payload.request.SpotConfirmationRequest;
import com.parkinglot.payload.response.TicketResponse;
import com.parkinglot.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/issueTicket")
    public ResponseEntity<?> issueTicket(@RequestBody TicketDto ticketDto ){
        try {
            TicketResponse ticketResponse = ticketService.issueTicket(ticketDto);
            return ResponseEntity.ok(ticketResponse);
        } catch (TicketException e) {
            return ResponseEntity.badRequest().body("Invalid ticket request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }


    @PostMapping("/confirmSpot")
    public ResponseEntity<String> confirmSpot(@RequestBody SpotConfirmationRequest confirmationRequest) {
        try {
            ticketService.spotConfirmation(confirmationRequest.getTicketId(),
                                           confirmationRequest.getSpotId());
            return ResponseEntity.ok("Parking spot confirmed successfully.");
        }catch (TicketException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error confirming parking spot: " + e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

}
