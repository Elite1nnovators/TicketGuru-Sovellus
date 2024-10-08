package com.eliteinnovators.ticketguru.ticketguru_app;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Customer;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.OrderDetails;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Salesperson;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.TicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.CustomerRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventTicketTypeRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.OrderDetailsRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.OrderRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.SalespersonRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketTypeRepository;

@SpringBootApplication
public class TicketguruAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketguruAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CustomerRepository customerRepository, EventRepository eventRepository, TicketRepository ticketRepository, TicketTypeRepository ticketTypeRepository, EventTicketTypeRepository eventTicketTypeRepository, SalespersonRepository salespersonRepository, OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository) {
        return (args) -> {
            TicketType ticketType = new TicketType("Aikuinen");
            TicketType ticketType2 = new TicketType("Lapsi");
            TicketType ticketType3 = new TicketType("VIP");
            TicketType ticketType4 = new TicketType("Eläkeläinen");
            ticketTypeRepository.save(ticketType);
            ticketTypeRepository.save(ticketType2);
            ticketTypeRepository.save(ticketType3);
            ticketTypeRepository.save(ticketType4);

            Event event = new Event("Concert 1", new Date(), "Event Address 1", "Helsinki", "A great concert event");
            Event event2 = new Event("Concert 2", new Date(), "Event Address 2", "Helsinki", "Another event");
            Event event3 = new Event("Opera", new Date(), "Event Address 3", "Espoo", "Fantastic Opera night!");
            eventRepository.save(event);
            eventRepository.save(event2);
            eventRepository.save(event3);

            EventTicketType eventTicketType = new EventTicketType(event, ticketType, 20, 50);
            EventTicketType eventTicketType2 = new EventTicketType(event, ticketType2, 10, 60);
            EventTicketType eventTicketType3 = new EventTicketType(event, ticketType3, 100, 15);
            eventTicketTypeRepository.save(eventTicketType);
            eventTicketTypeRepository.save(eventTicketType2);
            eventTicketTypeRepository.save(eventTicketType3);

            EventTicketType event2TicketType = new EventTicketType(event2, ticketType, 10, 20);
            EventTicketType event2TicketType2 = new EventTicketType(event2, ticketType2, 5, 20);
            EventTicketType event2TicketType3 = new EventTicketType(event2, ticketType3, 80, 5);
            eventTicketTypeRepository.save(event2TicketType);
            eventTicketTypeRepository.save(event2TicketType2);
            eventTicketTypeRepository.save(event2TicketType3);

            EventTicketType event3TicketType = new EventTicketType(event3, ticketType, 40, 50);
            EventTicketType event3TicketType2 = new EventTicketType(event3, ticketType2, 20, 20);
            EventTicketType event3TicketType3 = new EventTicketType(event3, ticketType4, 20, 20);
            eventTicketTypeRepository.save(event3TicketType);
            eventTicketTypeRepository.save(event3TicketType2);
            eventTicketTypeRepository.save(event3TicketType3);

            Customer customer = new Customer("john_doe", "password", new Date(), "John", "Doe", "123456789", "john@example.com", "Ensimmäinen kuja", "Helsinki", null);
            Customer customer2 = new Customer("jane_doe", "password2", new Date(), "Jane", "Doe", "987654321", "jane@example.com", "Toinen kuja", "Espoo", null);
            customerRepository.save(customer);
            customerRepository.save(customer2);

            Salesperson admin = new Salesperson("maijameikalainen", "password", true, "Maija", "Meikäläinen", "0501234567", null);
            Salesperson salesperson = new Salesperson("peter_smith", "password", false, "Peter", "Smith", "045123456", null);
            Salesperson salesperson2 = new Salesperson("anna_kokkonen", "password", false, "Anna", "Kokkonen", "045456321", null);
            salespersonRepository.save(salesperson);
            salespersonRepository.save(salesperson2);
            salespersonRepository.save(admin);

            Ticket ticket = new Ticket(eventTicketType, "hashedcode", true);
            Ticket ticket2 = new Ticket(eventTicketType2, "hashedcode2", true);
            Ticket ticket3 = new Ticket(eventTicketType3, "hashedcode3", true);
            ticketRepository.save(ticket);
            ticketRepository.save(ticket2);
            ticketRepository.save(ticket3);
            Order order = new Order(customer, new Date(), salesperson);
            orderRepository.save(order);

            OrderDetails orderDetails = new OrderDetails(order, 3, ticket2, 30);
            OrderDetails orderDetails2 = new OrderDetails(order, 4, ticket3, 10);
            orderDetailsRepository.save(orderDetails);
            orderDetailsRepository.save(orderDetails2);
        };
    }
}
