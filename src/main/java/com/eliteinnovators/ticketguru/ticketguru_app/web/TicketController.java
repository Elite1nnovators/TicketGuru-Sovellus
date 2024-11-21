package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;
import com.eliteinnovators.ticketguru.ticketguru_app.service.TicketService;


@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SALESPERSON')")
    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return ResponseEntity.status(HttpStatus.OK).body(tickets);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SALESPERSON')")
    @GetMapping("/tickets/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ticket);
    }

    // Hae liput tapahtuman mukaan
    @GetMapping("/tickets/event/{eventId}")
    public ResponseEntity<?> getTicketsByEventAndTicketCode(@PathVariable Long eventId, @RequestParam(required = false) String ticketCode) {
        if (ticketCode != null) {
            Ticket ticket = ticketService.getTicketByEventAndCode(eventId, ticketCode);
            return ResponseEntity.status(HttpStatus.OK).body(ticket);
        } else {
        List<Ticket> tickets = ticketService.getTicketsByEvent(eventId);
        return ResponseEntity.status(HttpStatus.OK).body(tickets);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SALESPERSON')")
    // Hae liput tilauksen mukaan
    @GetMapping("/tickets/order/{orderId}")
    public ResponseEntity<List<Ticket>> getTicketsByOrder(@PathVariable Long orderId) {
        List<Ticket> tickets = ticketService.getTicketsByOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(tickets);
    }


    @PatchMapping("/tickets/event/{eventId}")
    public ResponseEntity<Ticket> patchTicketByTicketCode(@PathVariable Long eventId, @RequestParam String ticketCode, @RequestBody Map<String, Object> updates) {
        Ticket patchedTicket = ticketService.patchTicketByTicketCode(ticketCode, eventId, updates);
        return ResponseEntity.status(HttpStatus.OK).body(patchedTicket);
    }
    

    
    

}
