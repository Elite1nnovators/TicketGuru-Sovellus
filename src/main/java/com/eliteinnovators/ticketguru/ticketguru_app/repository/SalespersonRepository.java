package com.eliteinnovators.ticketguru.ticketguru_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Salesperson;

public interface SalespersonRepository extends JpaRepository<Salesperson,Long>{

    Optional<Salesperson> findBySalespersonId(Long salespersonId);

}
