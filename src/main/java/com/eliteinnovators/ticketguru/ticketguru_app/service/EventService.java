package com.eliteinnovators.ticketguru.ticketguru_app.service;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Date;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Transactional
    public Event createNewEvent(Event newEvent) {
        return eventRepository.save(newEvent);
    }

    @Transactional
    public Event editEvent(Event editedEvent, Long eventId) {
        editedEvent.setEventId(eventId);
        return eventRepository.save(editedEvent);
    }

    @Transactional
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    @Transactional
    public Event patchEvent(Long eventId, Map<String, Object> updates) {
        Event event = getEventById(eventId);

        updates.forEach((key, value) -> {
            switch (key) {
                case "eventName":
                    event.setEventName((String) value);
                    break;
                case "eventDate":
                    event.setEventDate((Date) value);
                    break;
                case "eventAddress":
                    event.setEventAddress((String) value);
                    break;
                case "eventCity":
                    event.setEventCity((String) value);
                    break;
                case "eventDescription":
                    event.setEventDescription((String) value);
                    break;
                default:
                    throw new RuntimeException("Field " + key + " not found");
            }
        });

        return eventRepository.save(event);
    }

    public List<Event> searchEventsByCity(String eventCity) {
        List<Event> events = eventRepository.findByEventCity(eventCity);
        if (events.isEmpty()) {
            throw new RuntimeException("Event with the city name " + eventCity + " not found");
        }
        return events;
    }
}