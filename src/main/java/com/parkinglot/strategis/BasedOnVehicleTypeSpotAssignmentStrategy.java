package com.parkinglot.strategis;

import com.parkinglot.exceptions.ParkingSpotException;
import com.parkinglot.modal.*;
import com.parkinglot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasedOnVehicleTypeSpotAssignmentStrategy implements SpotAssignmentStrategy{
    @Autowired
    private ParkingLotRepository parkingLotRepository;
    private ParkingSpot getParkingSpot(List<ParkingSpot> spots,VehicleType vehicleType){
        switch (vehicleType){
            case CAR -> {
              return  spots
                        .stream()
                        .filter(spot->spot instanceof CarParkingSpot
                                &&  spot.getStatus().equals(ParkingSpotStatus.AVAILABLE))
                        .findFirst()
                        .orElse(null);

            }
            case BIKE -> {
                return  spots
                        .stream()
                        .filter(spot->spot instanceof BikeParkingSpot
                                &&  spot.getStatus().equals(ParkingSpotStatus.AVAILABLE))
                        .findFirst()
                        .orElse(null);
            }
            case BUS -> {
                return  spots
                        .stream()
                        .filter(spot->spot instanceof BusParkingSpot
                                &&  spot.getStatus().equals(ParkingSpotStatus.AVAILABLE))
                        .findFirst()
                        .orElse(null);
            }
        }
        return null;
    }
    @Override
    public ParkingSpot getSpot(ParkingLot parkingLot,VehicleType vehicleType) {
        List<ParkingFloor> floors = parkingLot.getFloors();
        for(ParkingFloor floor:floors){
            ParkingSpot spot = getParkingSpot(floor.getParkingSpotsList(), vehicleType);
            if(spot!=null){
                return spot;
            }
        }

        throw new ParkingSpotException("Unable to park the vehicle. All parking spots are currently occupied. Please try again later or choose another parking lot.");
    }
}
