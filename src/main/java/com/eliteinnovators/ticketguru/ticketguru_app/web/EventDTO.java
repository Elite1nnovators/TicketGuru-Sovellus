package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.time.LocalDate;
import java.util.List;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;

public class EventDTO {

    private Long eventId;
    private String eventName;
    private LocalDate eventDate;
    private String eventCity;
    private List<EventTicketType> eventTicketTypes;

   
    public EventDTO() {
    }

 
    public EventDTO(Long eventId, String eventName, LocalDate eventDate, String eventCity, List<EventTicketType> eventTicketTypes) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventCity = eventCity;
        this.eventTicketTypes = eventTicketTypes;
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

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventCity() {
        return eventCity;
    }

    public void setEventCity(String eventCity) {
        this.eventCity = eventCity;
    }

    public List<EventTicketType> getEventTicketTypes() {
        return eventTicketTypes;
    }

    public void setEventTicketTypes(List<EventTicketType> eventTicketTypes) {
        this.eventTicketTypes = eventTicketTypes;
    }
}
