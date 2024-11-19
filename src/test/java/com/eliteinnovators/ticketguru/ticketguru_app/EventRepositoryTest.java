package com.eliteinnovators.ticketguru.ticketguru_app;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventRepository;

@SpringBootTest
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void testFindByEventNameAndEventCity() {
        Event event = new Event();
        event.setEventName("Sample Event");
        event.setEventCity("Sample City");
        event = eventRepository.save(event);

        List<Event> events = eventRepository.findByEventNameAndEventCity("Sample Event", "Sample City");
        assertFalse(events.isEmpty());
        assertEquals("Sample City", events.get(0).getEventCity());
    }

    @Test
    public void testFindAllByEventName() {
        Event event1 = new Event();
        event1.setEventName("Sample Event");
        event1.setEventCity("City 1");
        eventRepository.save(event1);

        Event event2 = new Event();
        event2.setEventName("Sample Event");
        event2.setEventCity("City 2");
        eventRepository.save(event2);

        List<Event> events = eventRepository.findAllByEventName("Sample Event");
        assertEquals(2, events.size());
    }

    @Test
    public void testFindByEventName() {
        Event event = new Event();
        event.setEventName("Unique Event");
        event.setEventCity("Sample City");
        event = eventRepository.save(event);

        Optional<Event> found = eventRepository.findByEventName("Unique Event");
        assertTrue(found.isPresent());
        assertEquals("Unique Event", found.get().getEventName());
    }

    @Test
    public void testFindByEventCity() {
        Event event = new Event();
        event.setEventName("Another Event");
        event.setEventCity("Target City");
        event = eventRepository.save(event);

        List<Event> events = eventRepository.findByEventCity("Target City");
        assertFalse(events.isEmpty());
        assertEquals("Target City", events.get(0).getEventCity());
    }
}
