package com.eliteinnovators.ticketguru.ticketguru_app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.TicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventTicketTypeRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketTypeRepository;

@SpringBootTest
public class EventTicketTypeRepositoryTest {

    @Autowired
    private EventTicketTypeRepository eventTicketTypeRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Test
    public void testFindByEvent_EventId() {
        Event event = new Event();
        event.setEventName("Sample Event");
        event.setEventCity("Sample City");
        event = eventRepository.save(event);

        EventTicketType ett = new EventTicketType();
        ett.setEvent(event);
        ett.setPrice(100.0);
        ett.setTicketsInStock(50);
        eventTicketTypeRepository.save(ett);

        List<EventTicketType> result = eventTicketTypeRepository.findByEvent_EventId(event.getEventId());
        assertFalse(result.isEmpty());
        assertEquals(100.0, result.get(0).getPrice());
    }

    @Test
    public void testFindByEventAndTicketType() {
        Event event = new Event();
        event.setEventName("Sample Event");
        event.setEventCity("Sample City");
        event = eventRepository.save(event);

        TicketType ticketType = new TicketType();
        ticketType.setName("VIP");
        ticketType = ticketTypeRepository.save(ticketType);

        EventTicketType ett = new EventTicketType();
        ett.setEvent(event);
        ett.setTicketType(ticketType);
        ett.setPrice(200.0);
        ett.setTicketsInStock(20);
        eventTicketTypeRepository.save(ett);

        Optional<EventTicketType> result = eventTicketTypeRepository.findByEventAndTicketType(event, ticketType);
        assertTrue(result.isPresent());
        assertEquals(200.0, result.get().getPrice());
        assertEquals("VIP", result.get().getTicketType().getName());
    }
}
