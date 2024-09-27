package com.eliteinnovators.ticketguru.ticketguru_app.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class EventTicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "ticketType_id")
    private TicketType ticketType;

    private double price; 
    private int ticketsInStock; 

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

