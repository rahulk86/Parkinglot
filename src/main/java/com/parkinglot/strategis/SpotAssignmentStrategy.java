package com.parkinglot.strategis;

import com.parkinglot.modal.ParkingLot;
import com.parkinglot.modal.ParkingSpot;
import com.parkinglot.modal.VehicleType;

public interface SpotAssignmentStrategy {
     ParkingSpot getSpot(ParkingLot parkingLot, VehicleType vehicleType);
}
