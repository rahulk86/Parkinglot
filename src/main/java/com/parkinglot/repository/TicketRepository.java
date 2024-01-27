package com.parkinglot.repository;

import com.parkinglot.modal.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,String> {

}
