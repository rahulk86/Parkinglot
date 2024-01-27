package com.parkinglot.repository;

import com.parkinglot.modal.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer ,Long> {

}
