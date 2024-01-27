package com.parkinglot.repository;

import com.parkinglot.modal.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot,Long> {
   Optional<ParkingSpot> findBySlotNumber(String slotNumber);
}
