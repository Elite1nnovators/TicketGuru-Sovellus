package com.eliteinnovators.ticketguru.ticketguru_app;

import java.util.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.*;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.*;
import com.eliteinnovators.ticketguru.ticketguru_app.service.*;
import com.eliteinnovators.ticketguru.ticketguru_app.web.*;

@SpringBootApplication
public class TicketguruAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketguruAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(
            CustomerRepository customerRepository, 
            EventRepository eventRepository, 
            TicketRepository ticketRepository, 
            TicketTypeRepository ticketTypeRepository, 
            EventTicketTypeRepository eventTicketTypeRepository, 
            SalespersonRepository salespersonRepository, 
            OrderService orderService) {

        return (args) -> {
            // 1. Create and save ticket types
            TicketType ticketType = new TicketType("Aikuinen");
            TicketType ticketType2 = new TicketType("Lapsi");
            ticketTypeRepository.saveAll(Arrays.asList(ticketType, ticketType2));

            // 2. Create and save events
            Event event = new Event("Concert 1", new Date(), "Event Address 1", "Helsinki", "A great concert event");
            eventRepository.save(event);

            // 3. Create and save event ticket types
            EventTicketType eventTicketType = new EventTicketType(event, ticketType, 20, 50);
            EventTicketType eventTicketType2 = new EventTicketType(event, ticketType2, 15, 100);
            eventTicketTypeRepository.saveAll(Arrays.asList(eventTicketType, eventTicketType2));

            // 4. Create and save customers and salespersons
            Customer customer = new Customer("john_doe", "password", new Date(), "John", "Doe", "123456789", "john@example.com", "Street 1", "Helsinki", null);
            customerRepository.save(customer);

            Salesperson salesperson = new Salesperson("peter_smith", "password", false, "Peter", "Smith", "0451234567", null);
            salespersonRepository.save(salesperson);

            // 5. Create order details
            OrderDetailsDTO orderDetailDTO1 = new OrderDetailsDTO(eventTicketType.getId(), 2, eventTicketType.getPrice());
            OrderDetailsDTO orderDetailDTO2 = new OrderDetailsDTO(eventTicketType2.getId(), 1, eventTicketType2.getPrice());

            // 6. Create an OrderDTO
            OrderDTO orderDTO = new OrderDTO(
                    customer.getCustomerId(), 
                    salesperson.getSalespersonId(), 
                    Arrays.asList(orderDetailDTO1, orderDetailDTO2),
                    null, // Order ID will be generated
                    null, null, // Customer's first and last name will be set by the service
                    null, null, // Salesperson's first and last name will be set by the service
                    new Date()
            );

            // 7. Create a new order using the OrderService
            OrderDTO createdOrder = orderService.newOrder(orderDTO);
            System.out.println("Created Order: " + createdOrder);
        };
    }

}
