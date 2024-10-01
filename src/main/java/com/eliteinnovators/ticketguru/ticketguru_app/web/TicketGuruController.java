package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventRepository;

@RestController
public class TicketGuruController {

    @Autowired
    EventRepository eRepo;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/events")
    public Iterable<Event> getAllEvents() {
        return eRepo.findAll();
    }

    @GetMapping("/event/{eventId}")
    Event getEventById(@PathVariable Long eventId) {
        return eRepo.findById(eventId).orElse(null);
    }

      @PostMapping("/event")
    public Event newEvent(@RequestBody Event newEvent) {
        for (EventTicketType eventTicketType : newEvent.getEventTicketTypes()) {
            eventTicketType.setEvent(newEvent); 
        }
        return eRepo.save(newEvent); 
    }

    @PutMapping("event/{eventId}")
    Event editEvent(@RequestBody Event editedEvent, @PathVariable Long eventId) {
        editedEvent.setEventId(eventId);
        return eRepo.save(editedEvent);
    }

    @DeleteMapping("event/{eventId}")
    public Iterable<Event> deleteEvent(@PathVariable("eventId") Long eventId) {
        eRepo.deleteById(eventId);
        return eRepo.findAll();
    }

}
