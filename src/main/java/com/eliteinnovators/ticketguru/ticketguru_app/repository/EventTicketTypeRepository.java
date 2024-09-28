package com.eliteinnovators.ticketguru.ticketguru_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.TicketType;

public interface EventTicketTypeRepository extends JpaRepository<EventTicketType, Long> {
    
    
    EventTicketType findByEventAndTicketType(Event event, TicketType ticketType);
}
