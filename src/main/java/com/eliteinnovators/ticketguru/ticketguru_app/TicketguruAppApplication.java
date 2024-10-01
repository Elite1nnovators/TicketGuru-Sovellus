package com.eliteinnovators.ticketguru.ticketguru_app;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.eliteinnovators.ticketguru.ticketguru_app.repository.CustomerRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventTicketTypeRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketTypeRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Customer;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.TicketType;

@SpringBootApplication
public class TicketguruAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketguruAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository customerRepository, EventRepository eventRepository, TicketRepository ticketRepository, TicketTypeRepository ticketTypeRepository, EventTicketTypeRepository eventTicketTypeRepository) {
		return (args) -> {
			TicketType ticketType = new TicketType("Aikuinen");
			TicketType ticketType2 = new TicketType("Lapsi");
			TicketType ticketType3 = new TicketType("VIP");
        	ticketTypeRepository.save(ticketType);
        	ticketTypeRepository.save(ticketType2);
        	ticketTypeRepository.save(ticketType3);

			Event event = new Event("Concert 1", new Date(), "Event Address 1", "Helsinki", "A great concert event");
			Event event2 = new Event("Concert 2", new Date(), "Event Address 2", "Helsinki", "Another event");
			eventRepository.save(event);
			eventRepository.save(event2);

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

			Customer customer = new Customer("john_doe", "password", new Date(), "John", "Doe", "123456789", "john@example.com", "Ensimmäinen kuja", "Helsinki", null);
			Customer customer2 = new Customer("jane_doe", "password2", new Date(), "Jane", "Doe", "987654321", "jane@example.com", "Toinen kuja", "Espoo", null);
			customerRepository.save(customer);
			customerRepository.save(customer2);

			Ticket ticket = new Ticket("hashedcode", true, ticketType, event);
			Ticket ticket2 = new Ticket("hashedcode2", true, ticketType2, event);
			Ticket ticket3 = new Ticket("hashedcode3", true, ticketType3, event);
			Ticket ticket4 = new Ticket("hashedcode4", true, ticketType, event2);
			Ticket ticket5 = new Ticket("hashedcode5", true, ticketType2, event2);
			Ticket ticket6 = new Ticket("hashedcode6", true, ticketType3, event2);
			ticketRepository.save(ticket);
			ticketRepository.save(ticket2);
			ticketRepository.save(ticket3);
			ticketRepository.save(ticket4);
			ticketRepository.save(ticket5);
			ticketRepository.save(ticket6);
		};
	}
}
