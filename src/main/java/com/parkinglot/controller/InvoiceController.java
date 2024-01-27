package com.parkinglot.controller;

import com.parkinglot.dtos.InvoiceDto;
import com.parkinglot.dtos.PaymentDto;
import com.parkinglot.exceptions.InvoiceException;
import com.parkinglot.payload.response.InvoiceResponse;
import com.parkinglot.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/issueInvoice")
    public ResponseEntity<?> issueInvoice(@RequestBody InvoiceDto invoiceDto ){
        try {
            InvoiceResponse invoice = invoiceService.issueInvoice(invoiceDto);
            return ResponseEntity.ok(invoice);
        } catch (InvoiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }



    @PostMapping("/receiveVehicle/{invoiceNumber}")
    public ResponseEntity<?> receiveVehicle(@PathVariable String invoiceNumber) {
        try {
            invoiceService.receiveVehicle(invoiceNumber);
            return ResponseEntity.ok("Vehicle received successfully");
        } catch (InvoiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    @PostMapping("/{invoiceNumber}/makePayment")
    public ResponseEntity<?> makePayment(@PathVariable String invoiceNumber, @RequestBody PaymentDto paymentDto) {
        try {
            PaymentDto payment = invoiceService.makePayment(invoiceNumber, paymentDto);
            return ResponseEntity.ok(payment);
        } catch (InvoiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }



}
