package com.eliteinnovators.ticketguru.ticketguru_app.web;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;
    
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getAllEvents());
    }

    
    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable Long eventId) {
        Event event = eventService.getEventById(eventId);
        return ResponseEntity.status(HttpStatus.OK).body(event);
    }

    
    @PostMapping
    public ResponseEntity<?> createEvent(@Valid @RequestBody Event newEvent, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        Event createdEvent = eventService.createNewEvent(newEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    
    @PutMapping("/{eventId}")
    public ResponseEntity<?> editEvent(@Valid @RequestBody Event editedEvent, BindingResult bindingResult, @PathVariable Long eventId) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        Event updatedEvent = eventService.editEvent(editedEvent, eventId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEvent);
    }

    
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Object> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    
    @GetMapping("/search")
    public ResponseEntity<List<Event>> searchEventsByCity(@RequestParam(required = false) String eventCity) {
        List<Event> events;
        if (eventCity != null && !eventCity.isEmpty()) {
            events = eventService.searchEventsByCity(eventCity);
            if (events.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(events);
            }
        } else {
            events = eventService.getAllEvents();
        }
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

    
    @PatchMapping("/{eventId}")
    public ResponseEntity<Event> patchEvent(@PathVariable Long eventId, @RequestBody Map<String, Object> updates) {
        Event patchedEvent = eventService.patchEvent(eventId, updates);
        return ResponseEntity.status(HttpStatus.OK).body(patchedEvent);
    }
}
