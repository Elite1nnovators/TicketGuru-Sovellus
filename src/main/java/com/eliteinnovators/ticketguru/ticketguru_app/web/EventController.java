package com.eliteinnovators.ticketguru.ticketguru_app.web;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.mapper.EventMapper;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }
    // HTTP statukset kun EventMapper on luotu toimivasti

    @GetMapping("/{eventId}")
    public Event getEventById(@PathVariable Long eventId) {
        return eventService.getEventById(eventId);
    }

    @PostMapping()
    public ResponseEntity<?> createEvent(@Valid @RequestBody Event newEvent, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        Event createdEvent = eventService.createNewEvent(newEvent);
        return ResponseEntity.ok(createdEvent);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<?> editEvent(@Valid @RequestBody Event editedEvent, BindingResult bindingResult, @PathVariable Long eventId) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        Event updatedEvent = eventService.editEvent(editedEvent, eventId);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
    }

    @GetMapping("/search")
    public List<Event> searchEventsByCity(@RequestParam(required = false) String eventCity) {
        if (eventCity != null && !eventCity.isEmpty()) {
            return eventService.searchEventsByCity(eventCity);
        } else {
            return eventService.getAllEvents();
        }
    }

    @PatchMapping("/{eventId}")
    public Event patchEvent(@PathVariable Long eventId, @RequestBody Map<String, Object> updates) {
        return eventService.patchEvent(eventId, updates);
    }
}
