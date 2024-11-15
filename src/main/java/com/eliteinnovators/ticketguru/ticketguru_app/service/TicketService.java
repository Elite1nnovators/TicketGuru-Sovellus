package com.eliteinnovators.ticketguru.ticketguru_app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;
import com.eliteinnovators.ticketguru.ticketguru_app.exception.TicketNotFoundException;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventTicketTypeRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketRepository;

// TODO TicketDto
@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventTicketTypeRepository eventTicketTypeRepository;

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

    // Hae lippukoodin mukaan
    public Ticket getTicketByEventAndCode(Long eventId, String ticketCode) {
        return ticketRepository.findByEventTicketType_Event_EventIdAndTicketCode(eventId, ticketCode)
        .stream()
        .findFirst()
        .orElseThrow(() -> new TicketNotFoundException("Ticket with code " + ticketCode + " not found"));
    }
        
    @Transactional
    public Ticket patchTicketByTicketCode(String ticketCode, Long eventId, Map<String, Object> updates) {
        Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
        .orElseThrow(() -> new TicketNotFoundException("Ticket with code " + ticketCode + " not found"));

        if (!ticket.getEventTicketType().getEvent().getEventId().equals(eventId)) {
            throw new TicketNotFoundException("Ticket with code " + ticketCode + " does not belong to event " + eventId);
        }
        if (!ticket.isValid()) {
            throw new IllegalStateException("Ticket with code " + ticketCode + " has already been used");
        }

        ticket.setValid(false);

        updates.forEach((key, value) -> {
            switch (key) {
                case "ticketCode":
                    ticket.setTicketCode((String) value);
                default:
                    throw new IllegalArgumentException("Invalid update key: " + key);
            }
        });
        
        return ticketRepository.save(ticket);
    }
        

    


}
