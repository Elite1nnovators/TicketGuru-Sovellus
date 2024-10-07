package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.ArrayList;

@Entity
public class EventTicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonBackReference 
    private Event event;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

    @OneToMany(mappedBy = "eventTicketType", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Ticket> tickets = new ArrayList<>();

    private double price; 
    private int ticketsInStock; 

    public EventTicketType() {}

    public EventTicketType(Event event, TicketType ticketType, double price, int ticketsInStock) {
        this.event = event;
        this.ticketType = ticketType;
        this.price = price;
        this.ticketsInStock = ticketsInStock;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
    
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
        for (Ticket ticket : tickets) {
            ticket.setEventTicketType(this);
        }
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
    
}


