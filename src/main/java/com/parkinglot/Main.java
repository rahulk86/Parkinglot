package com.parkinglot;

import com.parkinglot.modal.*;
import com.parkinglot.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext run = SpringApplication.run(Main.class, args);
        ParkingLotRepository parkingLotRepository = run.getBean(ParkingLotRepository.class);
        ParkingSpotRepository parkingSpotRepository = run.getBean(ParkingSpotRepository.class);
        //// first ---
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setActive(true);
        parkingLot.setAddress("banglore rt nagar");
        parkingLot.setCreatAt(LocalDateTime.now());
        parkingLot.setName("parking lot");
        parkingLotRepository.save(parkingLot);
//
// second ---

        ParkingFloorRepository floorRepository        = run.getBean(ParkingFloorRepository.class);
//        ParkingSpotRepository parkingSpotRepository   = run.getBean(ParkingSpotRepository.class);


        ParkingFloor floor = new ParkingFloor();
        floor.setFloorNumber(1);
        floor.setActive(true);
        floor.setCreatAt(LocalDateTime.now());
        floor.setParkingLot(parkingLotRepository.findById(1L).get());
        SpotDecorator decorator = new SpotDecorator();
        decorator.setCreatAt(LocalDateTime.now());
        decorator.setActive(true);
        floor.setSpotDecorator(parkingSpotRepository.save(decorator));
        floorRepository.save(floor);
        floor.setId(null);
        floor.setFloorNumber(2);
        decorator.setId(null);
        decorator.setCreatAt(LocalDateTime.now());
        decorator.setActive(true);
        floor.setSpotDecorator(parkingSpotRepository.save(decorator));
        floorRepository.save(floor);

// third ---

        GateRepository gateRepository = run.getBean(GateRepository.class);
        Gate gate = new Gate();
        gate.setActive(true);
        gate.setCreatAt(LocalDateTime.now());
        gate.setGateNumber("Entry G.N 1");
        gate.setGateType(GateType.ENTRY);
        gate.setParkingLot(parkingLotRepository.findById(1L).get());
        gateRepository.save(gate);

        gate.setId(null);
        gate.setGateNumber("Entry G.N 2");
        gateRepository.save(gate);

        Gate exitgate = new Gate();
        exitgate.setActive(true);
        exitgate.setCreatAt(LocalDateTime.now());
        exitgate.setGateNumber("Exit G.N 1");
        exitgate.setGateType(GateType.EXIT);
        exitgate.setParkingLot(parkingLotRepository.findById(1L).get());
        gateRepository.save(exitgate);

        exitgate.setId(null);
        exitgate.setGateNumber("Exit G.N 2");
        gateRepository.save(exitgate);

// fourth ---
//
//        GateRepository gateRepository = run.getBean(GateRepository.class);
        OperatorRepository operatorRepository = run.getBean(OperatorRepository.class);

        Operator operator = new Operator();
        operator.setName("vikash kumar");
        operator.setActive(true);
        operator.setCreatAt(LocalDateTime.now());
        gate = gateRepository.findById(1L).orElse(null);
        if(gate!=null) {
            gate.setCurrentOperator(operatorRepository.save(operator));
            gateRepository.save(gate);
        }

        operator.setId(null);
        operator.setName("rahul kumar");
        gate = gateRepository.findById(2L).orElse(null);
        if(gate!=null) {
            gate.setCurrentOperator(operatorRepository.save(operator));
            gateRepository.save(gate);
        }

        operator.setId(null);
        operator.setName("dheeraj kumar");
        gate = gateRepository.findById(3L).orElse(null);
        if(gate!=null) {
            gate.setCurrentOperator(operatorRepository.save(operator));
            gateRepository.save(gate);
        }

        operator.setId(null);
        operator.setName("prashant kumar");
        gate = gateRepository.findById(4L).orElse(null);
        if(gate!=null) {
            gate.setCurrentOperator(operatorRepository.save(operator));
            gateRepository.save(gate);
        }

// fifth ---
        createSlot("G.F",run,1L);
        createSlot("First.F",run,2L);



    }

    private static void createSlot(String slotNoPrefix,ApplicationContext context, Long parkingFloorId){
        ParkingSpotRepository parkingSpotRepository = context.getBean(ParkingSpotRepository.class);

        ParkingFloorRepository floorRepository = context.getBean(ParkingFloorRepository.class);

        ParkingFloor parkingFloor = floorRepository.findById(parkingFloorId).get();
        creatSlot("Bike "+slotNoPrefix,parkingSpotRepository,parkingFloor,new BikeParkingSpot());
        creatSlot("Car "+slotNoPrefix,parkingSpotRepository,parkingFloor,new CarParkingSpot());
        creatSlot("Bus "+slotNoPrefix,parkingSpotRepository,parkingFloor,new BusParkingSpot());
        floorRepository.save(parkingFloor);
    }
    private static void creatSlot(String slotNoPrefix,ParkingSpotRepository repository,
                                  ParkingFloor floor,
                                  ParkingSpot spot){
        int count = 5;
         while(count-->0){
             ParkingSpot newSpot = spot.clone();
             newSpot.setStatus(ParkingSpotStatus.AVAILABLE);
             newSpot.setActive(true);
             newSpot.setCreatAt(LocalDateTime.now());
             newSpot.setDecorator(floor.getSpotDecorator());
             newSpot.setSlotNumber(slotNoPrefix+" "+count);
             floor.setSpotDecorator(repository.save(newSpot));
         }
    }

}