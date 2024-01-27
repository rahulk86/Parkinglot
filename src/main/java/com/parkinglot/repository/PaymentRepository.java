package com.parkinglot.repository;

import com.parkinglot.modal.Invoice;
import com.parkinglot.modal.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
      Optional<Payment> findByReferenceNumber(String referenceNumber);
      List<Payment> findByInvoice(Invoice invoice);
}
