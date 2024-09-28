package com.eliteinnovators.ticketguru.ticketguru_app;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.eliteinnovators.ticketguru.ticketguru_app.repository.CustomerRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketTypeRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Customer;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.TicketType;

@SpringBootApplication
public class TicketguruAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketguruAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository customerRepository, EventRepository eventRepository, TicketRepository ticketRepository, TicketTypeRepository ticketTypeRepository) {
		return (args) -> {
			TicketType ticketType = new TicketType("Aikuinen", 12.50);
			TicketType ticketType2 = new TicketType("Lapsi", 2.50);
        	ticketTypeRepository.save(ticketType);
        	ticketTypeRepository.save(ticketType2);

			Event event = new Event("Concert 1", new Date(), "Event Address 1", "Helsinki", "A great concert event");
			Event event2 = new Event("Concert 2", new Date(), "Event Address 2", "Helsinki", "Another event");
			eventRepository.save(event);
			eventRepository.save(event2);

			Customer customer = new Customer("john_doe", "password", new Date(), "John", "Doe", "123456789", "john@example.com", "Ensimmäinen kuja", "Helsinki", null);
			customerRepository.save(customer);

			Ticket ticket = new Ticket("hashedcode", true, ticketType, event);
			Ticket ticket2 = new Ticket("hashedcode2", true, ticketType2, event2);
			ticketRepository.save(ticket);
			ticketRepository.save(ticket2);

			Customer customer2 = new Customer("jane_doe", "password2", new Date(), "Jane", "Doe", "987654321", "jane@example.com", "Toinen kuja", "Espoo", null);
			customerRepository.save(customer2);
			Ticket ticket3 = new Ticket("hashedcode3", true, ticketType, event);
			ticketRepository.save(ticket3);

		};
	}
	

}
