package com.eliteinnovators.ticketguru.ticketguru_app.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;
import com.eliteinnovators.ticketguru.ticketguru_app.exception.TicketNotFoundException;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventTicketTypeRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.OrderRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventTicketTypeRepository eventTicketTypeRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Haetaan kaikki liput
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // Haetaan lippu ID:llä
    public Ticket getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId)
        .orElseThrow(() -> new TicketNotFoundException("Ticket with ID " + ticketId + " not found"));
    }

    // Lipun voimassaolon tarkistaminen
    public boolean isTicketValid(Long ticketId) {
        Ticket ticket = getTicketById(ticketId);
        return ticket.isValid();
    }

    // Lipun poistaminen (TARVITAANKO TÄMÄ?)
    public void deleteTicket(Long ticketId) {
        Ticket ticket = getTicketById(ticketId);
        ticketRepository.delete(ticket);
    }

      // Hae kaikki lipputyypit tapahtumalle
    public List<Ticket> getTicketsByEvent(Long eventId) {
    List<EventTicketType> ticketTypes = eventTicketTypeRepository.findByEvent_EventId(eventId);

    List<Ticket> tickets = new ArrayList<>();

    // Käy läpi kaikki lipputyypit ja hae niiden liput
    for (EventTicketType ticketType : ticketTypes) {
        List<Ticket> ticketsForType = ticketRepository.findByEventTicketTypeId(ticketType.getId());
        tickets.addAll(ticketsForType);
    }

    return tickets;
}

    // Hae liput tilauksen mukaan
    public List<Ticket> getTicketsByOrder(Long orderId) {
        return ticketRepository.findByOrder_OrderId(orderId);
    }
        


}
