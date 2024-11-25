package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class EventTicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonBackReference(value = "event-eventTicketType")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    @JsonBackReference(value = "ticketType-eventTicketType")
    private TicketType ticketType;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private double price; 

    @NotNull(message = "Tickets in stock cannot be null")
    @Min(value = 0, message = "Tickets in stock cannot be negative")
    private int ticketsInStock; 

    @OneToMany(mappedBy = "eventTicketType", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "eventTicketType-ticket")
    private List<Ticket> ticket = new ArrayList<>();

    public EventTicketType() {}
    public EventTicketType(Event event, TicketType ticketType, double price, int ticketsInStock) {
        this.event = event;
        this.ticketType = ticketType;
        this.price = price;
        this.ticketsInStock = ticketsInStock;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Event getEvent() {
        return event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }
    public TicketType getTicketType() {
        return ticketType;
    }
    public List<Ticket> getTickets() {
        return ticket;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getTicketsInStock() {
        return ticketsInStock;
    }
    public void setTicketsInStock(int ticketsInStock) {
        this.ticketsInStock = ticketsInStock;
    }

     @JsonProperty("ticketTypeName")
    public String getTicketTypeName() {
        if (ticketType != null) {
            return ticketType.getName();  // Returns the name of the ticket type
        }
        return null;  // In case the ticketType is null
    }
}


