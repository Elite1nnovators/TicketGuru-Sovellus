package com.eliteinnovators.ticketguru.ticketguru_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByEventTicketTypeId(Long eventTicketTypeId);
    List<Ticket> findByOrder_OrderId(Long orderId);
    Optional<Ticket> findByTicketCode(String ticketCode);
   
}
