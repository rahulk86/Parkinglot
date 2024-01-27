package com.parkinglot.service;

import com.parkinglot.configuration.security.AppProperties;
import com.parkinglot.dtos.InvoiceDto;
import com.parkinglot.dtos.PaymentDto;
import com.parkinglot.exceptions.InvoiceException;
import com.parkinglot.exceptions.TicketException;
import com.parkinglot.modal.*;
import com.parkinglot.payload.response.InvoiceResponse;
import com.parkinglot.repository.*;
import com.parkinglot.strategis.FeeCalculationStrategy;
import com.parkinglot.strategis.FeeCalculationStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class InvoiceService {

    @Autowired
    private GateRepository gateRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Autowired
    private ParkingSpotTransactionRepository parkingSpotTransactionRepository;

    @Autowired
    private AppProperties appProperties;
    public InvoiceResponse issueInvoice(InvoiceDto invoiceDto){
        Ticket ticket = ticketRepository
                            .findById(invoiceDto.getTicketId())
                            .orElseThrow(() -> new InvoiceException("Ticket with ID " + invoiceDto.getTicketId() + " not found"));
        Operator operator = operatorRepository
                              .findById(invoiceDto.getOperatorId())
                              .orElseThrow(()-> new InvoiceException("Operator with ID " + invoiceDto.getOperatorId() + " not found"));
        Gate gate = gateRepository
                .findByGateNumber(invoiceDto.getGateNumber())
                .orElseThrow(()->new InvoiceException("Gate with ID " + invoiceDto.getGateNumber() + " not found"));
        Invoice generatedInvoice = invoiceRepository.findByTicket(ticket).orElse(null);
        if(generatedInvoice!=null){
            throw new InvoiceException("An invoice has already been generated for this ticket at " + generatedInvoice.getExitTime() + ". Please check your records or contact support for assistance.");
        }

        ParkingSpotTransaction confirmSpotTransaction = parkingSpotTransactionRepository
                                                            .findByTicket(ticket)
                                                            .orElse(null);
        if (confirmSpotTransaction ==null) {
            throw new TicketException("Spot confirmation not completed. Please confirm your parking spot to proceed.");
        }

        Invoice invoice                               = new Invoice();
        FeeCalculationStrategy feeCalculationStrategy = FeeCalculationStrategyFactory
                                                                .getFeeCalculationStrategy(ticket
                                                                        .getCustomer()
                                                                        .getVehicle()
                                                                        .getVehicleType());
        if(feeCalculationStrategy==null){
            throw new InvoiceException("");
        }

        invoice.setTicket(ticket);
        invoice.setGeneratedAt(gate);
        invoice.setGeneratedBy(operator);
        invoice.setExitTime(appProperties.now());
        invoice.setId(generateInvoiceNumber());
        invoice.setActive(true);
        invoice.setFee(feeCalculationStrategy.calculateFee(ticket.getEntryTime(),invoice.getExitTime()));
        Invoice save = invoiceRepository.save(invoice);
        return InvoiceResponse
                .builder()
                .id(save.getId())
                .fee(save.getFee())
                .operatorName(save.getGeneratedBy().getName())
                .build();
    }


    public void receiveVehicle(String invoiceNumber) {
        Invoice invoice = getInvoiceByNumber(invoiceNumber);

        Payment successfulPayment = paymentRepository
                                        .findByInvoice(invoice)
                                        .stream()
                                        .filter(payment -> payment.getPaymentStatus().equals(PaymentStatus.SUCCESS))
                                        .findFirst()
                                        .orElse(null);
        if (successfulPayment == null) {
            throw new InvoiceException("No successful payment found for the invoice");
        }

        ParkingSpotTransaction confirmSpotTransaction = parkingSpotTransactionRepository
                                                            .findByTicket(invoice.getTicket())
                                                            .get();

        if (confirmSpotTransaction.getExitTime() != null) {
            throw new TicketException("You have already exited from the parking. Spot confirmation cannot be completed again.");
        }

        ParkingSpot parkingSpot = invoice.getTicket().getParkingSpot();

        confirmSpotTransaction.setExitTime(appProperties.now());
        parkingSpotTransactionRepository.save(confirmSpotTransaction);

        parkingSpot.setStatus(ParkingSpotStatus.AVAILABLE);
        parkingSpotRepository.save(parkingSpot);
        parkingSpot.scheduleMaintenance();

    }

    private Invoice getInvoiceByNumber(String invoiceNumber) {
        return invoiceRepository
                .findById(invoiceNumber)
                .orElseThrow(() -> new InvoiceException("Invoice with ID " + invoiceNumber + " not found"));
    }

    public PaymentDto makePayment(String invoiceNumber, PaymentDto paymentDto){
        Invoice invoice = getInvoiceByNumber(invoiceNumber);
        Payment successfulPayment = paymentRepository
                .findByInvoice(invoice)
                .stream()
                .filter(payment -> payment.getPaymentStatus().equals(PaymentStatus.SUCCESS))
                .findFirst()
                .orElse(null);
        if (successfulPayment != null) {
            throw new InvoiceException("Invoice already has a successful payment");
        }

        Payment payment = new Payment();
        payment.setPaymentMode(PaymentMode.getPaymentMode(paymentDto.getPaymentMode()));
        if(paymentDto.getAmount() != invoice.getFee()){
          payment.setPaymentStatus(PaymentStatus.FAILED);
        }
        else{
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
        }
        payment.setTime(appProperties.now());
        payment.setReferenceNumber(generateReferenceNumber());
        payment.setInvoice(invoice);
        Payment save = paymentRepository.save(payment);

        return PaymentDto
                .builder()
                .paymentMode(save.getPaymentMode().getDisplayName())
                .referenceNumber(save.getReferenceNumber())
                .amount(save.getInvoice().getFee())
                .status(save.getPaymentStatus())
                .invoiceNumber(save.getInvoice().getId())
                .build();
    }
    public  String generateReferenceNumber() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        long timestamp = System.currentTimeMillis();
        return uuid + timestamp;
    }

    public static String generateInvoiceNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String datePart = dateFormat.format(new Date());
        String uuidPart = UUID.randomUUID().toString().substring(0, 6);
        return "INV-" + datePart + "-" + uuidPart;
    }
}
