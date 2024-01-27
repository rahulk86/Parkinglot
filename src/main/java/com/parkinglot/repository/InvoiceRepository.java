package com.parkinglot.repository;

import com.parkinglot.modal.Invoice;
import com.parkinglot.modal.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice,String> {
   Optional<Invoice> findByTicket(Ticket ticket);
}
