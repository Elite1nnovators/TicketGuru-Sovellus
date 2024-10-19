package com.eliteinnovators.ticketguru.ticketguru_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.TicketType;

public interface EventTicketTypeRepository extends JpaRepository<EventTicketType, Long> {
    
    List<EventTicketType> findByEvent_EventId(Long eventId);
    EventTicketType findByEventAndTicketType(Event event, TicketType ticketType);
}
