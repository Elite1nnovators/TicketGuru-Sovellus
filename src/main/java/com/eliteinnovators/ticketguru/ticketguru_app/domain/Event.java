package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import java.util.List;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;



@Entity
public class Event {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @NotBlank(message = "Event name is mandatory")
    private String eventName;

   /* @FutureOrPresent  --- Tämä ei toimi ainakaan demodatalla */
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm") 
   private LocalDateTime eventDate;

    private int advanceSaleHours = 12; // Ennakkomyynti (oletus 12h ennen eventDatea)

    private String eventAddress;

    @NotBlank(message = "Event city is mandatory")
    private String eventCity;
    private String eventDescription;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "event-eventTicketType")
    private List<EventTicketType> eventTicketTypes = new ArrayList<>(); 

    public Event() {
    }

    public Event(String eventName, LocalDateTime eventDate, String eventAddress, String eventCity, String eventDescription) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventAddress = eventAddress;
        this.eventCity = eventCity;
        this.eventDescription = eventDescription;
    }

    public boolean isAdvanceSaleActive() {
        LocalDateTime cutoffTime = eventDate.toInstant(ZoneOffset.UTC)
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDateTime()
                                            .minusHours(advanceSaleHours);
        return LocalDateTime.now().isBefore(cutoffTime);
    }

    public List<EventTicketType> getEventTicketTypes() {
        return eventTicketTypes;
    }

    public void setEventTicketTypes(List<EventTicketType> eventTicketTypes) {
        this.eventTicketTypes = eventTicketTypes;
        for (EventTicketType eventTicketType : eventTicketTypes) {
            eventTicketType.setEvent(this);
        }
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

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
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

    public int getAdvanceSaleHours() {
        return advanceSaleHours;
    }

    public void setAdvanceSaleHours(int advanceSaleHours) {
        this.advanceSaleHours = advanceSaleHours;
    }
}
