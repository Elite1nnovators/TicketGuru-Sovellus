package com.eliteinnovators.ticketguru.ticketguru_app;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.eliteinnovators.ticketguru.ticketguru_app.repository.CustomerRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Customer;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;

@SpringBootApplication
public class TicketguruAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketguruAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository customerRepository, EventRepository eventRepository) {
		return (args) -> {
			// Add sample data here to test your setup
			customerRepository.save(new Customer("john_doe", "password", new Date(), "John", "Doe", "123456789", "john@example.com", "123 Main St", "Helsinki", null));
			eventRepository.save(new Event("Concert", new Date(), "Event Address", "Helsinki", "A great concert event"));
		};
	}
	

}
