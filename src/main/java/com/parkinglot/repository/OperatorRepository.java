package com.parkinglot.repository;

import com.parkinglot.modal.Invoice;
import com.parkinglot.modal.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator,Long> {

}
