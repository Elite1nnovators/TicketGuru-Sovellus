package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;
import com.eliteinnovators.ticketguru.ticketguru_app.service.TicketService;

@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return ResponseEntity.status(HttpStatus.OK).body(tickets);
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ticket);
    }

    // Hae liput tapahtuman mukaan
    @GetMapping("/tickets/event/{eventId}")
    public ResponseEntity<List<Ticket>> getTicketsByEvent(@PathVariable Long eventId) {
        List<Ticket> tickets = ticketService.getTicketsByEvent(eventId);
        return ResponseEntity.status(HttpStatus.OK).body(tickets);
    }

    // Hae liput tilauksen mukaan
    @GetMapping("/tickets/order/{orderId}")
    public ResponseEntity<List<Ticket>> getTicketsByOrder(@PathVariable Long orderId) {
        List<Ticket> tickets = ticketService.getTicketsByOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(tickets);
    }
    

}
