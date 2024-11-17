package com.eliteinnovators.ticketguru.ticketguru_app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.OrderDetails;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventTicketTypeRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.OrderDetailsRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.OrderRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.SalespersonRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.service.OrderService;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.OrderDetails;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Salesperson;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;

@DataJpaTest
public class OrderDetailsRepositoryTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SalespersonRepository salespersonRepository;

    @Autowired
    private EventTicketTypeRepository eventTicketTypeRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void testFindByOrderDetailId() {
        // Create and save a Salesperson
        // Create and save a Salesperson
        Salesperson salesperson = new Salesperson();
        salesperson.setFirstName("John");
        salesperson.setUsername("johndoe"); // Set a valid username
        salesperson.setLastName("Doe"); // Set a valid last name
        salesperson.setPasswordHash("securepasswordhash"); // Set a valid password hash
        salesperson = salespersonRepository.save(salesperson);

        // Create and save an EventTicketType
        EventTicketType eventTicketType = new EventTicketType();
        eventTicketType.setPrice(50.0);
        eventTicketType.setTicketsInStock(100);
        eventTicketType = eventTicketTypeRepository.save(eventTicketType);

        // Create and save a Ticket
        Ticket ticket = new Ticket();
        ticket.setValid(true);
        ticket.setEventTicketType(eventTicketType);
        ticket = ticketRepository.save(ticket);

        // Create and save OrderDetails
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setEventTicketType(eventTicketType);
        orderDetails.setUnitPrice(50.0);
        orderDetails.setQuantity(2);

        // Create and save an Order
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setSalesperson(salesperson);
        order.setTickets(Collections.singletonList(ticket)); // Satisfying "must contain at least one ticket"
        order.setOrderDetails(Collections.singletonList(orderDetails)); // Associate OrderDetails with the Order
        order = orderRepository.save(order);

        // Save the OrderDetails with the persisted Order
        orderDetails.setOrder(order);
        orderDetails = orderDetailsRepository.save(orderDetails);

        // Find and assert
        OrderDetails found = orderDetailsRepository.findByOrderDetailId(orderDetails.getOrderDetailId());
        assertNotNull(found);
        assertEquals(50.0, found.getUnitPrice());
        assertEquals(2, found.getQuantity());
    }
}
