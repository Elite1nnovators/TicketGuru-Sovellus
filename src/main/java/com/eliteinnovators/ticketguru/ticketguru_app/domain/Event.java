package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Date;



@Entity
public class Event {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    private String eventName;
    private Date eventDate;
    private String eventAddress;
    private String eventCity;
    private String eventDescription;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @JsonIgnore
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "event")
    @JsonManagedReference
    private List<EventTicketType> eventTicketTypes;

    public Event() {
    }

    public Event(String eventName, Date eventDate, String eventAddress, String eventCity, String eventDescription) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventAddress = eventAddress;
        this.eventCity = eventCity;
        this.eventDescription = eventDescription;
    }

    public List<EventTicketType> getEventTicketTypes() {
        return eventTicketTypes;
    }

    public void setEventTicketTypes(List<EventTicketType> eventTicketTypes) {
        this.eventTicketTypes = eventTicketTypes;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getEventCity() {
        return eventCity;
    }

    public void setEventCity(String eventCity) {
        this.eventCity = eventCity;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
