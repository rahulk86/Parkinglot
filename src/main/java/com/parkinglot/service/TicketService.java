package com.parkinglot.service;

import com.parkinglot.configuration.TicketNumberGenerator;
import com.parkinglot.configuration.security.AppProperties;
import com.parkinglot.dtos.TicketDto;
import com.parkinglot.exceptions.TicketException;
import com.parkinglot.modal.*;
import com.parkinglot.payload.response.TicketResponse;
import com.parkinglot.repository.*;
import com.parkinglot.strategis.SpotAssignmentStrategy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    @Qualifier("ticketConfigModelMapper")
    private ModelMapper ticketModelMapper;

    @Autowired
    private SpotAssignmentStrategy spotAssignmentStrategy;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Autowired
    private GateRepository gateRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkingSpotTransactionRepository parkingSpotTransactionRepository;

    @Autowired
    private AppProperties appProperties;
    public TicketResponse issueTicket(TicketDto ticketDto){
        Ticket ticket     = new Ticket();

        Customer customer = new Customer();
        customer.setName(ticketDto.getName());
        customer.setLicenceNumber(ticketDto.getLicenceNumber());
        customer.setActive(true);
        customer.setCreatAt(appProperties.now());


        Vehicle vehicle   = vehicleRepository
                                  .findByVehicleNumber(ticketDto.getVehicleNumber())
                                  .orElseGet(()->{
                                      Vehicle vehicle1 = new Vehicle();
                                      vehicle1.setVehicleNumber(ticketDto.getVehicleNumber());
                                      vehicle1.setVehicleType(ticketDto.getVehicleType());
                                      return vehicle1;
                                  });
        vehicle.setActive(true);
        vehicle.setCreatAt(appProperties.now());
        customer.setVehicle(vehicleRepository.save(vehicle));


        Gate gate         = gateRepository
                                .findById(ticketDto.getGateId())
                                .orElseThrow(() -> new TicketException("Gate with ID " + ticketDto.getGateId() + " not found"));

        Operator operator = operatorRepository
                                .findById(ticketDto.getOperatorId())
                                .orElseThrow(() -> new TicketException("Operator with ID " + ticketDto.getOperatorId() + " not found"));

        ticket.setId(TicketNumberGenerator.generateRandomTicketNumber(8));
        ticket.setCustomer(customerRepository.save(customer));
        ticket.setGeneratedAt(gate);
        ticket.setGeneratedBy(operator);
        ticket.setActive(true);
        ticket.setEntryTime(appProperties.now());
        ParkingSpot spot = spotAssignmentStrategy.getSpot(gate.getParkingLot(), ticket.getCustomer().getVehicle().getVehicleType());
        spot.setStatus(ParkingSpotStatus.RESERVED);
        ticket.setParkingSpot(parkingSpotRepository.save(spot));
        Ticket save = ticketRepository.save(ticket);
        return TicketResponse
                .builder()
                .operatorName(save.getGeneratedBy().getName())
                .gateNumber(save.getGeneratedAt().getGateNumber())
                .id(save.getId())
                .slotNumber(spot.getSlotNumber())
                .name(save.getCustomer().getName())
                .build();
    }

    public void spotConfirmation(String ticketId,String spotId){
        ParkingSpot spot = (ParkingSpot)parkingSpotRepository
                            .findBySlotNumber(spotId)
                            .orElseThrow(() -> new TicketException("Spot with ID " + spotId + " not found"));
        Ticket ticket = ticketRepository
                            .findById(ticketId)
                            .orElseThrow(()->new TicketException("Spot with ID " + spotId + " not found"));

        ParkingSpotTransaction confirmSpotTransaction = parkingSpotTransactionRepository
                                                        .findByTicket(ticket)
                                                        .orElse(null);

        if (confirmSpotTransaction != null && confirmSpotTransaction.getExitTime() == null) {
            throw new TicketException("Spot confirmation already completed. You have already confirmed your parking spot.");
        }
        else if(confirmSpotTransaction != null){
          throw new TicketException("You have already exited from the parking. Spot confirmation cannot be completed again.");
        }
        ParkingSpot parkingSpot = ticket.getParkingSpot();

        if (!parkingSpot.getSlotNumber().equals(spotId)) {
            throw new TicketException("Invalid operation. The provided spot ID " + spotId +
                    " does not match the ticket's assigned spot ID " + parkingSpot.getSlotNumber() + ".");
        }

        ParkingSpotTransaction spotTransaction = new ParkingSpotTransaction();
        spotTransaction.setTicket(ticket);
        spotTransaction.setEntryTime(appProperties.now());
        parkingSpotTransactionRepository.save(spotTransaction);
        spot.setStatus(ParkingSpotStatus.OCCUPIED);
        parkingSpotRepository.save(spot);
    }

}
