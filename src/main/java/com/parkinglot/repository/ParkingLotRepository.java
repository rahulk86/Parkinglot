package com.parkinglot.repository;

import com.parkinglot.modal.Customer;
import com.parkinglot.modal.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot,Long> {

}
