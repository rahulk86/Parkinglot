package com.parkinglot.repository;

import com.parkinglot.modal.ParkingSpotTransaction;
import com.parkinglot.modal.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingSpotTransactionRepository extends JpaRepository<ParkingSpotTransaction,Long> {
    Optional<ParkingSpotTransaction> findByTicket(Ticket ticket);
}
