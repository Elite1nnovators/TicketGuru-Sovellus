package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventRepository;

@RestController
public class TicketGuruController {

    @Autowired
    EventRepository eRepo;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // EVENTTIEN REST -ENDPOINTIT
    @GetMapping("/events")
    public Iterable<Event> getAllEvents() {
        return eRepo.findAll();
    }

    @GetMapping("/events/{eventId}")
    Event getEventById(@PathVariable Long eventId) {
        return eRepo.findById(eventId).orElse(null);
    }

    @PostMapping("/events")
    public Event newEvent(@RequestBody Event newEvent) {
        return eRepo.save(newEvent);
    }

    @PutMapping("events/{eventId}")
    Event editEvent(@RequestBody Event editedEvent, @PathVariable Long eventId) {
        editedEvent.setEventId(eventId);
        return eRepo.save(editedEvent);
    }

    @DeleteMapping("events/{eventId}")
    public Iterable<Event> deleteEvent(@PathVariable("eventId") Long eventId) {
        eRepo.deleteById(eventId);
        return eRepo.findAll();
    }

    @GetMapping("/events/")
    public List<Event> searchEvents(@RequestParam(required = false) String eventName, @RequestParam(required = false) String eventCity) {
        if (eventName != null && !eventName.isEmpty() && eventCity != null && !eventCity.isEmpty()) {
            return eRepo.findByEventNameAndEventCity(eventName, eventCity);
        } else if (eventName != null && !eventName.isEmpty()) {
            return eRepo.findByEventName(eventName);
        } else if (eventCity != null && !eventCity.isEmpty()) {
            return eRepo.findByEventCity(eventCity);
        }
        return eRepo.findAll();
    }

}
