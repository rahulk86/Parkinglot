package com.parkinglot.repository;

import com.parkinglot.modal.Customer;
import com.parkinglot.modal.ParkingFloor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingFloorRepository extends JpaRepository<ParkingFloor,Long> {

}
