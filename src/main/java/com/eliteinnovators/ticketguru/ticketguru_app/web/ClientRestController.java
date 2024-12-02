package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.eliteinnovators.ticketguru.ticketguru_app.repository.UserRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.service.EventService;
import com.eliteinnovators.ticketguru.ticketguru_app.service.OrderService;
import com.eliteinnovators.ticketguru.ticketguru_app.service.SalespersonService;
import com.eliteinnovators.ticketguru.ticketguru_app.service.TicketService;
import com.eliteinnovators.ticketguru.ticketguru_app.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class ClientRestController {

    @Autowired
    private EventService eventService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private SalespersonRepository salespersonRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SalespersonService salespersonService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @PostMapping("/sell")
    public ResponseEntity<?> sellTickets(
            @Valid @RequestBody SellTicketsDto request,
            Authentication authentication) {

        Long selectedEventId = request.getSelectedEventId();
        int quantity = request.getQuantity();
        String ticketTypeString = request.getTicketType();
        boolean soldAtDoor = request.isSoldAtDoor();

        String username = authentication.getName();
        Event selectedEvent = eventService.getEventById(selectedEventId);

        if (selectedEvent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Event with ID " + selectedEventId + " not found.");
        }

        if (quantity <= 0) {
            return ResponseEntity.badRequest().body("Quantity must be greater than zero.");
        }

        boolean advanceSaleActive = selectedEvent.isAdvanceSaleActive();
        if (advanceSaleActive && soldAtDoor) {
            return ResponseEntity.badRequest().body("Tickets cannot be marked as 'sold at the door' during the advance sales period.");
        }

        if (!advanceSaleActive && !soldAtDoor) {
            return ResponseEntity.badRequest().body("Advance sales are over. Tickets must be sold at the door.");
        }

        EventTicketType selectedEventTicketType = null;
        List<EventTicketType> eventTicketTypes = selectedEvent.getEventTicketTypes();
        for (EventTicketType e : eventTicketTypes) {
            if (e.getTicketTypeName().equals(ticketTypeString)) {
                selectedEventTicketType = e;
                break;
            }
        }
        if (selectedEventTicketType == null) {
            return ResponseEntity.badRequest().body("Invalid ticket type selection.");
        }

        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
        orderDetailsDTO.setEventTicketTypeId(selectedEventTicketType.getId());
        orderDetailsDTO.setQuantity(quantity);

        OrderDTO orderDTO = new OrderDTO();
        SalespersonDTO salespersonDTO = salespersonService.getSalespersonByUsername(username);

        orderDTO.setSalespersonId(salespersonDTO.getSalespersonId());
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

    @GetMapping("/print-tickets/{orderId}")
    public ResponseEntity<?> getOrderTicketCodes(
            @PathVariable Long orderId,
            @RequestParam(required = false) String ticketType) {
        try {
            // Fetch tickets based on orderId and optional ticketType
            List<String> ticketCodes = ticketService.getTicketCodesByOrderId(orderId);

            if (ticketCodes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "No tickets found for this order"));
            }

            return ResponseEntity.ok(Map.of("ticketCodes", ticketCodes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An error occurred while fetching ticket codes"));
        }
    }

    @PostMapping("/add/user")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        
        if (userDTO.getSalesperson() != null && userDTO.getSalesperson().getSalespersonId() != null) {
            SalespersonDTO salespersonDTO = salespersonService
                    .getSalespersonById(userDTO.getSalesperson().getSalespersonId());

            if (salespersonDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Salesperson with ID " + userDTO.getSalesperson().getSalespersonId() + " not found.");
            }

            
            userDTO.setSalesperson(salespersonDTO);
        }

        
        UserDTO createdUser = userService.createUser(userDTO, userDTO.getPassword());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/users/{Id}")
    public ResponseEntity<?> getUserById(@PathVariable Long Id) {
        UserDTO user = userService.getUserById(Id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/salespersons")
    public ResponseEntity<List<SalespersonDTO>> getAllSalespersons() {
        List<SalespersonDTO> salespersons = salespersonService.getAllSalespersons();
        return new ResponseEntity<>(salespersons, HttpStatus.OK);
    }

    @PostMapping("/add/salesperson")
    public ResponseEntity<?> createSalesperson(@RequestBody SalespersonDTO salespersonDTO) {
        SalespersonDTO createdSalesperson = salespersonService.createSalesperson(salespersonDTO);
        return new ResponseEntity<>(createdSalesperson, HttpStatus.CREATED);
    }

    @GetMapping("/users/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated.");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("-------AUTHENTICATED USER: " + username);
        UserDTO user = userService.getUserByUsername(username);
        

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping("/users/edit/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody Map<String, Object> userDTO) {
        UserDTO updatedUser = userService.patchUser(userId, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/users/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/salespersons/edit/{salespersonId}")
    public ResponseEntity<?> updateSalesperson(@PathVariable Long salespersonId, @RequestBody Map<String, Object> salespersonDTO) {
        SalespersonDTO updatedSalesperson = salespersonService.patchSalesperson(salespersonId, salespersonDTO);
        return new ResponseEntity<>(updatedSalesperson, HttpStatus.OK);
    }

    @DeleteMapping("/salespersons/delete/{salespersonId}")
    public ResponseEntity<?> deleteSalesperson(@PathVariable Long salespersonId) {
        salespersonService.deleteSalesperson(salespersonId);
        return ResponseEntity.noContent().build();
    }

}
