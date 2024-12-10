package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Salesperson;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.SellTicketsDto;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.TicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.exception.InsufficientTicketsException;
import com.eliteinnovators.ticketguru.ticketguru_app.exception.SalespersonNotFoundException;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventTicketTypeRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.SalespersonRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketTypeRepository;
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
    private EventRepository eventRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EventTicketTypeRepository eventTicketTypeRepository;

    @Autowired
    private SalespersonService salespersonService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/sell")
    public ResponseEntity<?> sellTickets(
            @Valid @RequestBody SellTicketsDto request,
            Authentication authentication) {

        Long selectedEventId = request.getSelectedEventId();
        boolean soldAtDoor = request.isSoldAtDoor();

        String username = authentication.getName();
        Event selectedEvent = eventService.getEventById(selectedEventId);

        if (selectedEvent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Event with ID " + selectedEventId + " not found.");
        }

        if (request.getTicketSelections().isEmpty()) {
            return ResponseEntity.badRequest().body("Quantity must be greater than zero.");
        }

        boolean advanceSaleActive = selectedEvent.isAdvanceSaleActive();
        if (advanceSaleActive && soldAtDoor) {
            return ResponseEntity.badRequest()
                    .body("Tickets cannot be marked as 'sold at the door' during the advance sales period.");
        }

        if (!advanceSaleActive && !soldAtDoor) {
            return ResponseEntity.badRequest().body("Advance sales are over. Tickets must be sold at the door.");
        }

        List<OrderDetailsDTO> orderDetailsList = new ArrayList<>();
        for(SellTicketsDto.TicketSelection selection : request.getTicketSelections()) {
            String ticketTypeString = selection.getTicketType();
            int quantity = selection.getQuantity();

            if(quantity <= 0) {
                return ResponseEntity.badRequest().body("Quantity must be greater than zero.");
            }

            // Find the EventTicketType for this ticketType
            EventTicketType selectedEventTicketType = null;
            for (EventTicketType e : selectedEvent.getEventTicketTypes()) {
                if (e.getTicketTypeName().equals(ticketTypeString)) {
                    selectedEventTicketType = e;
                    break;
                }
            }

            if (selectedEventTicketType == null) {
                return ResponseEntity.badRequest().body("Invalid ticket type selection: " + ticketTypeString);
            }

            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
            orderDetailsDTO.setEventTicketTypeId(selectedEventTicketType.getId());
            orderDetailsDTO.setQuantity(quantity);
            // The unit price is set in the OrderService, no need to set here if not required.

            orderDetailsList.add(orderDetailsDTO);
        }

        OrderDTO orderDTO = new OrderDTO();
        SalespersonDTO salespersonDTO = salespersonService.getSalespersonByUsername(username);
        orderDTO.setSalespersonId(salespersonDTO.getSalespersonId());
        orderDTO.setOrderDetails(orderDetailsList);

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
    public ResponseEntity<?> updateSalesperson(@PathVariable Long salespersonId,
            @RequestBody Map<String, Object> salespersonDTO) {
        SalespersonDTO updatedSalesperson = salespersonService.patchSalesperson(salespersonId, salespersonDTO);
        return new ResponseEntity<>(updatedSalesperson, HttpStatus.OK);
    }

    @DeleteMapping("/salespersons/delete/{salespersonId}")
    public ResponseEntity<?> deleteSalesperson(@PathVariable Long salespersonId) {
        salespersonService.deleteSalesperson(salespersonId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/tickettypes/{id}")
    public ResponseEntity<TicketType> updateTicketType(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        TicketType ticketType = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TicketType not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    ticketType.setName((String) value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid key: " + key);
            }
        });

        TicketType updatedTicketType = ticketTypeRepository.save(ticketType);
        return new ResponseEntity<>(updatedTicketType, HttpStatus.OK);
    }

    @PatchMapping("/eventtickettypes/{id}")
    public ResponseEntity<EventTicketType> updateEventTicketType(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        EventTicketType eventTicketType = eventTicketTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EventTicketType not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "price":
                    eventTicketType.setPrice(((Number) value).doubleValue());
                    break;
                case "ticketsInStock":
                    eventTicketType.setTicketsInStock(((Number) value).intValue());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid key: " + key);
            }
        });

        EventTicketType updatedEventTicketType = eventTicketTypeRepository.save(eventTicketType);
        return new ResponseEntity<>(updatedEventTicketType, HttpStatus.OK);
    }

    @DeleteMapping("/tickettypes/{id}")
    public ResponseEntity<Void> deleteTicketType(@PathVariable Long id) {
        TicketType ticketType = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TicketType not found"));

        if (!ticketType.getEventTicketTypes().isEmpty()) {
            throw new RuntimeException("Cannot delete TicketType associated with events");
        }

        ticketTypeRepository.delete(ticketType);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/eventtickettypes/{id}")
    public ResponseEntity<Void> deleteEventTicketType(@PathVariable Long id) {
        EventTicketType eventTicketType = eventTicketTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EventTicketType not found"));

        if (!eventTicketType.getTickets().isEmpty()) {
            throw new RuntimeException("Cannot delete EventTicketType with existing tickets");
        }

        eventTicketTypeRepository.delete(eventTicketType);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/eventtickettypes")
    public ResponseEntity<Map<String, Object>> createTicketType(@RequestBody Map<String, Object> requestData) {
        String ticketTypeName = (String) requestData.get("ticketTypeName");
        Double price = Double.valueOf(requestData.get("price").toString());
        Integer ticketsInStock = (Integer) requestData.get("ticketsInStock");
        Long eventId = Long.valueOf(requestData.get("eventId").toString());

        if (ticketTypeName == null || ticketTypeName.length() < 2) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Ticket type name must be at least 2 characters long."));
        }

        // Find or create TicketType
        TicketType ticketType = ticketTypeRepository.findByName(ticketTypeName).orElseGet(() -> {
            TicketType newTicketType = new TicketType();
            newTicketType.setName(ticketTypeName);
            return ticketTypeRepository.save(newTicketType);
        });

        // Find Event
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event ID"));

        // Create EventTicketType
        EventTicketType eventTicketType = new EventTicketType(event, ticketType, price, ticketsInStock);
        eventTicketTypeRepository.save(eventTicketType);

        // Return Response
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "id", eventTicketType.getId(),
                "eventId", event.getEventId(),
                "ticketTypeName", ticketType.getName(),
                "price", eventTicketType.getPrice(),
                "ticketsInStock", eventTicketType.getTicketsInStock()));
    }

}
