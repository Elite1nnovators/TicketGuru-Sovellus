package com.eliteinnovators.ticketguru.ticketguru_app.repository;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {
    
}
