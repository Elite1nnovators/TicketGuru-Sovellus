package com.eliteinnovators.ticketguru.ticketguru_app;

import static org.junit.jupiter.api.Assertions.*;
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

import java.util.Collections;
import java.util.Date;

@SpringBootTest
@Transactional
public class OrderRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SalespersonRepository salespersonRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventTicketTypeRepository eventTicketTypeRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

@Test
public void testFindByOrderId() {
    // Luo eventin
    Event event = new Event();
    event.setEventName("Uusi tapahtuma");
    event.setEventCity("Helsinki");
    event = eventRepository.save(event);
  
    // Luo salespersonin
    Salesperson salesperson = new Salesperson();
    salesperson.setFirstName("John");
    salesperson.setUsername("johndoe");
    salesperson.setLastName("Doe");
    salesperson.setPasswordHash("securepasswordhash");
    salesperson = salespersonRepository.save(salesperson);
  
    // Luo TicketTypen
    TicketType ticketType = new TicketType();
    ticketType.setName("VIP");
    ticketType = ticketTypeRepository.save(ticketType);
  
    // Luo EventTicketTypen
    EventTicketType eventTicketType = new EventTicketType();
    eventTicketType.setEvent(event);
    eventTicketType.setTicketType(ticketType);
    eventTicketType.setPrice(50);
    eventTicketType = eventTicketTypeRepository.save(eventTicketType);
  
    // Luo uuden Ticketin 
    Ticket ticket = new Ticket();
    ticket.setEventTicketType(eventTicketType);
    ticket = ticketRepository.save(ticket); // Save Ticket
    ticketRepository.flush();

    // Luo OrderDetailsin
    OrderDetails orderDetails = new OrderDetails();
    orderDetails.setEventTicketType(eventTicketType);
    orderDetails.setUnitPrice(50.0);
    orderDetails.setQuantity(2);

    // Luo ja tallentaa ensin Orderin OrderDetailsillä
    Order order = new Order();
    order.setOrderDate(new Date());
    order.setSalesperson(salesperson);
    order.setTickets(Collections.singletonList(ticket)); // Add saved ticket
    order.setOrderDetails(Collections.singletonList(orderDetails)); // Associate OrderDetails with the Order
    order = orderRepository.save(order); // Save Order

    // Tallentaa Orderdetailsin oikealla yhteydellä orderiin
    orderDetails.setOrder(order);
    orderDetails = orderDetailsRepository.save(orderDetails); // Save OrderDetails

    // Hakee Orderin
    Order found = orderRepository.findByOrderId(order.getOrderId());
    assertNotNull(found);
    assertEquals(order.getOrderId(), found.getOrderId());
    assertEquals(salesperson.getUsername(), found.getSalesperson().getUsername());
    assertEquals(1, found.getTickets().size());
}

}
