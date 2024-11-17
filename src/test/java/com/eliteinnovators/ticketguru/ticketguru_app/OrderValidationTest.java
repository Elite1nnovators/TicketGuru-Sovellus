package com.eliteinnovators.ticketguru.ticketguru_app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.OrderDetails;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Salesperson;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@SpringBootTest
public class OrderValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.afterPropertiesSet();
        this.validator = factoryBean.getValidator();
    }

    @Test
    public void whenSalespersonIsNull_thenValidationFailure() {
        Order order = new Order();
        order.setSalesperson(null); // Jätetään salesperson null
        order.setOrderDate(new Date()); // Asetetaan nykyhetki
        order.setTickets(Collections.singletonList(new Ticket())); // Asetetaan lippu tilaukselle
        order.setOrderDetails(Collections.singletonList(new OrderDetails())); // Asetetaan tilaukselle Order Details

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertEquals(1, violations.size()); // Oletetaan, että tulee vain yksi virhe, sillä muut ovat valideja
        assertTrue(violations
        .stream()
        .anyMatch(v -> v.getMessage().equals("Order: Salesperson is required for the order"))); //Oletetaan, että saadaan validoinnin virheviesti
    }

    @Test
    public void whenOrderDetailsIsEmpty_thenValidationFailure() {
        Order order = new Order();
        order.setSalesperson(new Salesperson()); // Asetetaan myyjä
        order.setOrderDate(new Date()); // Asetetaan nykyhetki
        order.setTickets(Collections.singletonList(new Ticket())); // Asetetaan lippu tilaukselle
        order.setOrderDetails(Collections.emptyList()); // Asetetaan tyhjä lista

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertEquals(1, violations.size());
        assertTrue(violations
        .stream()
        .anyMatch(v -> v.getMessage().equals("Order: Order must have at least one order detail")));
    }

    @Test
    public void whenTicketsIsEmpty_thenValidationFailure() {
        Order order = new Order();
        order.setSalesperson(new Salesperson()); // Asetetaan myyjä
        order.setOrderDate(new Date()); // Asetetaan nykyhetki
        order.setTickets(Collections.emptyList()); // Asetetaan tyhjä lista
        order.setOrderDetails(Collections.singletonList(new OrderDetails())); // Asetetaan tilaukselle Order Details

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertEquals(1, violations.size());
        assertTrue(violations
        .stream()
        .anyMatch(v -> v.getMessage().equals("Order: Tickets must contain at least one ticket")));
    }

    @Test
    public void whenOrderDateIsInFuture_thenValidationFailure() {
        Order order = new Order();
        order.setSalesperson(new Salesperson()); // Asetetaan myyjä
        order.setOrderDate(new Date(System.currentTimeMillis() + 10000000)); // Asetetaan tuleva päivä
        order.setTickets(Collections.singletonList(new Ticket())); // Asetetaan lippu tilaukselle
        order.setOrderDetails(Collections.singletonList(new OrderDetails())); // Asetetaan tilaukselle Order Details

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertEquals(1, violations.size());
        assertTrue(violations
        .stream()
        .anyMatch(v -> v.getMessage().equals("Order date cannot be in the future")));
    }

    @Test
    public void whenAllFieldsAreInvalid_thenValidationFailure() {
        Order order = new Order();
        order.setSalesperson(null);
        order.setOrderDate(new Date(System.currentTimeMillis() + 10000000));
        order.setTickets(Collections.emptyList());
        order.setOrderDetails(Collections.emptyList());

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertEquals(4, violations.size());
    }

}
