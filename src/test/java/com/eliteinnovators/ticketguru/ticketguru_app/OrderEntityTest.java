package com.eliteinnovators.ticketguru.ticketguru_app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.OrderDetails;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Salesperson;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;

import jakarta.persistence.EntityManager;


public class OrderEntityTest {
    @Test
    public void testOrderFieldInitalization() {
        // Testataan, että Order-objektin kentät alustetaan oikein

        Order order = new Order();
        Salesperson salesperson = new Salesperson();
        order.setSalesperson(salesperson);
        order.setOrderDate(new Date());

        // Tarkastetaan, että objektillä on oikeat kentät ja ne on alustettu oikein
        assertNull(order.getOrderId(), "OrderId should be null before persisting");
        
        assertNotNull(order.getOrderDetails(), "OrderDetails should not be null");
        assertNotNull(order.getTickets(),"Tickets should not be null");
        assertNotNull(order.getSalesperson(), "Salesperson should not be null");
        assertNotNull(order.getOrderDate(), "OrderDate should not be null");

        //Tarkistetaan, että listat ovat aluksi tyhjiä
        assertTrue(order.getOrderDetails() instanceof List, "OrderDetails should be a List");
        assertTrue(order.getOrderDetails().isEmpty(), "OrderDetails list should be empty initially");

        assertTrue(order.getTickets() instanceof List, "Tickets should be a List");
        assertTrue(order.getTickets().isEmpty(), "Tickets list should be empty initially");
    }

    @Test
    public void testOrderEntityRelationships() {
        // Testataan entiteetin relaatioita muiden entiteettien kanssa

        Salesperson salesperson = new Salesperson();
        Order order = new Order(salesperson, new Date());

        OrderDetails orderDetails = new OrderDetails();
        Ticket ticket = new Ticket();

        order.setOrderDetails(Collections.singletonList(orderDetails));
        order.setTickets(Collections.singletonList(ticket));

        orderDetails.setOrder(order);
        ticket.setOrder(order);

        // Tarkastetaan, että OrderDetails ja Ticket on linkitetty oikein 
        assertEquals(1, order.getOrderDetails().size(), "Listassa pitäisi olla yksi OrderDetails, mutta löytyi: " + order.getOrderDetails().size());
        assertEquals(1, order.getTickets().size(), "Listassa pitäisi olla yksi Ticket, mutta löytyi: " + order.getTickets().size());
        assertEquals(order, orderDetails.getOrder(), "OrderDetails ei ole linkitetty oikeaan Orderiin.");
        assertEquals(order, ticket.getOrder(), "Ticket ei ole linkitetty oikeaan Orderiin.");
    }

    @Test
    public void testOrderLinking() {

        //Testataan, että entiteetti käyttäytyy odotetusti mockatussa persistenssikontekstissa

        EntityManager entityManager = mock(EntityManager.class);

        Salesperson salesperson = new Salesperson();
        salesperson.setFirstName("John");
        salesperson.setLastName("Doe");

        Order order = new Order();
        order.setSalesperson(salesperson);

        when(entityManager.find(Order.class, order.getOrderId())).thenReturn(order);

        Order foundOrder = entityManager.find(Order.class, order.getOrderId());

        assertNotNull(foundOrder, "Order pitäisi löytyä mock-EntityManagerista");
        assertEquals(salesperson, foundOrder.getSalesperson(), "Salesperson pitäisi olla linkitetty oikein");

    }

}
