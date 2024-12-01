package com.eliteinnovators.ticketguru.ticketguru_app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.OrderDetails;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Salesperson;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.TicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventTicketTypeRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.OrderDetailsRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.OrderRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.SalespersonRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketTypeRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class TicketRepositoryTest {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    TicketTypeRepository ticketTypeRepository;

    @Autowired
    EventTicketTypeRepository eventTicketTypeRepository;

    @Autowired
    SalespersonRepository salespersonRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Autowired
    EventRepository eventRepository;

    private EventTicketType eventTicketType;
    private Order order;
    private Ticket ticket1;

    @BeforeEach
    public void setUp() {
        // Luo eventin
        Event event = new Event();
        event.setEventName("Uusi tapahtuma");
        event.setEventCity("Helsinki");
        event = eventRepository.save(event);

        // Luo salespersonin
        Salesperson salesperson = new Salesperson();
        salesperson.setFirstName("John");
        salesperson.setLastName("Doe");
        salesperson = salespersonRepository.save(salesperson);

        // Luo TicketTypen
        TicketType ticketType = new TicketType();
        ticketType.setName("VIP");
        ticketType = ticketTypeRepository.save(ticketType);

        // Luo EventTicketTypen
        eventTicketType = new EventTicketType();
        eventTicketType.setEvent(event);
        eventTicketType.setTicketType(ticketType);
        eventTicketType.setPrice(50);
        eventTicketType = eventTicketTypeRepository.save(eventTicketType);

        // Luo uuden Ticketin
        ticket1 = new Ticket();
        ticket1.setEventTicketType(eventTicketType);
        ticket1.setOrder(null);
        ticket1 = ticketRepository.save(ticket1); //Tallentaa Ticketin
        ticketRepository.flush();

        // Luo OrderDetailsin
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setEventTicketType(eventTicketType);
        orderDetails.setUnitPrice(50.0);
        orderDetails.setQuantity(2);

        // Luo ja tallentaa ensin Orderin OrderDetailsillä
        order = new Order();
        order.setOrderDate(new Date());
        order.setSalesperson(salesperson);
        order.setTickets(Collections.singletonList(ticket1)); // Lisää tallennettu ticketti
        order.setOrderDetails(Collections.singletonList(orderDetails)); // Yhteys Orderin ja OrderDetailsin välille
        order = orderRepository.save(order); // Tallentaa orderdin

        ticket1.setOrder(order);  // Yhteys orderin ja ticketin välille
        ticketRepository.save(ticket1);

        // Tallentaa Orderdetailsin oikealla yhteydellä orderiin
        orderDetails.setOrder(order);
        orderDetails = orderDetailsRepository.save(orderDetails); // Tallentaa OrderDetailsin
    }

    @Test
    public void testFindByEventTicketTypeId() {
        List<Ticket> tickets = ticketRepository.findByEventTicketTypeId(eventTicketType.getId());
        assertEquals(1, tickets.size(), "There should be 1 ticket for this event ticket type.");
    }

    @Test
    public void testFindByOrder_OrderId() {
        List<Ticket> tickets = ticketRepository.findByOrder_OrderId(order.getOrderId());
        assertEquals(1, tickets.size(), "There should be 1 ticket for this order.");
    }

    @Test
    public void testFindByEventTicketType_Event_EventIdAndTicketCode() {
        List<Ticket> tickets = ticketRepository.findByEventTicketType_Event_EventIdAndTicketCode(
                eventTicketType.getEvent().getEventId(), ticket1.getTicketCode());
        assertEquals(1, tickets.size(), "There should be 1 ticket with the specified event ID and ticket code.");
        assertEquals(ticket1.getTicketCode(), tickets.get(0).getTicketCode(), "The ticket code should match.");
    }

    @Test
    public void testFindByTicketCode() {
        Optional<Ticket> ticketOptional = ticketRepository.findByTicketCode(ticket1.getTicketCode());
        assertTrue(ticketOptional.isPresent(), "Ticket should be found by ticket code.");
        assertEquals(ticket1.getTicketCode(), ticketOptional.get().getTicketCode(), "Ticket codes should match.");
    }

}
