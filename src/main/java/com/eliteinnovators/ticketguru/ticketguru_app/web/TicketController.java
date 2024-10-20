package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.service.TicketService;

@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketRepository ticketRepository;

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

}
