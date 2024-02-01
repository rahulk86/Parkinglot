package com.parkinglot.repository;

import com.parkinglot.modal.SpotDecorator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<SpotDecorator,Long> {
   Optional<SpotDecorator> findBySlotNumber(String slotNumber);
}
