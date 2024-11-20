package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Salesperson;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.SellTicketsDto;
import com.eliteinnovators.ticketguru.ticketguru_app.exception.InsufficientTicketsException;
import com.eliteinnovators.ticketguru.ticketguru_app.exception.SalespersonNotFoundException;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.SalespersonRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.service.EventService;
import com.eliteinnovators.ticketguru.ticketguru_app.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ClientRestController {

    @Autowired
    private EventService eventService;
    
    @Autowired
    private SalespersonRepository salespersonRepository;

    @Autowired
    private OrderService orderService;

    @PostMapping("/sell")
    public ResponseEntity<?> sellTickets(
        @Valid @RequestBody SellTicketsDto request,
        Authentication authentication)
    {

        Long selectedEventId = request.getSelectedEventId();
        int quantity = request.getQuantity();
        String ticketType = request.getTicketType();

        String username = authentication.getName();
        Event selectedEvent = eventService.getEventById(selectedEventId);

        if (selectedEvent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Event with ID " + selectedEventId + " not found.");
        }

        if (quantity <= 0) {
            return ResponseEntity.badRequest().body("Quantity must be greater than zero.");
        }

        int ticketTypeIndex = "VIP".equalsIgnoreCase(ticketType) ? 0 : 1;
        if (selectedEvent.getEventTicketTypes().size() <= ticketTypeIndex) {
            return ResponseEntity.badRequest().body("Invalid ticket type selection.");
        }

        EventTicketType selectedTicketType = selectedEvent.getEventTicketTypes().get(ticketTypeIndex);

        int availableTickets = selectedTicketType.getTicketsInStock();
        if (availableTickets < quantity) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Not enough tickets available. Available: " + availableTickets);
        }

        selectedTicketType.setTicketsInStock(availableTickets - quantity);

        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
        orderDetailsDTO.setEventTicketTypeId(selectedTicketType.getId());
        orderDetailsDTO.setQuantity(quantity);

        OrderDTO orderDTO = new OrderDTO();
        Salesperson salesperson = salespersonRepository.findByUsername(username)
                .orElseThrow(() -> new SalespersonNotFoundException("Salesperson with username " + username + " not found"));
        orderDTO.setSalespersonId(salesperson.getSalespersonId());
        orderDTO.setOrderDetails(List.of(orderDetailsDTO));

        try {
            OrderDTO savedOrder = orderService.newOrder(orderDTO);

            URI location = URI.create(String.format("/api/orders/%s", savedOrder.getOrderId()));

            return ResponseEntity.created(location).body(savedOrder);

        } catch (InsufficientTicketsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the order.");
        }
    }
}
