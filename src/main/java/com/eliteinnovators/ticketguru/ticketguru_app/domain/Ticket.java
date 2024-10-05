package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticketCode;
    private boolean isValid;

    @ManyToOne
    @JoinColumn(name = "eventTicketType_id")
    @JsonBackReference
    private EventTicketType eventTicketType;

    public Ticket() {
    }

    public Ticket(EventTicketType eventTicketType, String ticketCode, boolean isValid) {
        this.eventTicketType = eventTicketType;
        this.ticketCode = ticketCode;
        this.isValid = isValid;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public EventTicketType getEventTicketType() {
        return eventTicketType;
    }

    public void setEventTicketType(EventTicketType eventTicketType) {
        this.eventTicketType = eventTicketType;
    }

}
