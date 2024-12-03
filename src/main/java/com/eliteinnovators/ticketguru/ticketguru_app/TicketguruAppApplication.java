package com.eliteinnovators.ticketguru.ticketguru_app;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventTicketTypeRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.SalespersonRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketTypeRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.service.OrderService;
import com.eliteinnovators.ticketguru.ticketguru_app.web.OrderDTO;
import com.eliteinnovators.ticketguru.ticketguru_app.web.OrderDetailsDTO;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.*;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.*;
import com.eliteinnovators.ticketguru.ticketguru_app.service.*;
import com.eliteinnovators.ticketguru.ticketguru_app.web.*;



@SpringBootApplication
public class TicketguruAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketguruAppApplication.class, args);
    }

       @SuppressWarnings("unused")
    @Bean
    public CommandLineRunner demo(
            EventRepository eventRepository, 
            TicketRepository ticketRepository, 
            TicketTypeRepository ticketTypeRepository, 
            EventTicketTypeRepository eventTicketTypeRepository, 
            SalespersonRepository salespersonRepository, 
            OrderService orderService) {

        return (args) -> {
            // 1. Create and save ticket types if they don't exist
            TicketType aikuinen = ticketTypeRepository.findByName("Aikuinen")
                    .orElseGet(() -> ticketTypeRepository.save(new TicketType("Aikuinen")));
            TicketType lapsi = ticketTypeRepository.findByName("Lapsi")
                    .orElseGet(() -> ticketTypeRepository.save(new TicketType("Lapsi")));

            // 2. Create and save events if they don't exist
            Event concert1 = eventRepository.findByEventName("Concert 1")
                    .orElseGet(() -> {
			LocalDate localDatePlusOneYear = LocalDate.now().plusYears(1); // Add 1 year
                        ZonedDateTime datePlusOneYear = localDatePlusOneYear.atStartOfDay(ZoneId.systemDefault());
                        LocalDateTime localDateTimePlusOneYear = datePlusOneYear.toLocalDateTime();

                        Event event = new Event("Concert 1", localDateTimePlusOneYear, "Event Address 1", "Helsinki", "A great concert event");
                        return eventRepository.save(event);
                    });

            // 3. Create and save event ticket types if they don't exist
            EventTicketType etc1 = eventTicketTypeRepository.findByEventAndTicketType(concert1, aikuinen)
                    .orElseGet(() -> eventTicketTypeRepository.save(new EventTicketType(concert1, aikuinen, 20, 50)));
            EventTicketType etc2 = eventTicketTypeRepository.findByEventAndTicketType(concert1, lapsi)
                    .orElseGet(() -> eventTicketTypeRepository.save(new EventTicketType(concert1, lapsi, 15, 100)));

            // 4. Create and save salespersons if they don't exist
            Salesperson salesperson = salespersonRepository.findBySalespersonId(1L)
                    .orElseGet(() -> salespersonRepository.save(new Salesperson("Peter",  "Smith", "0451234567", null)));
            Salesperson admin = salespersonRepository.findBySalespersonId(2L)
                    .orElseGet(() -> salespersonRepository.save(new Salesperson("Anna", "Brown", "0409876543", null)));

            // 5. Create order details
            OrderDetailsDTO orderDetailDTO1 = new OrderDetailsDTO(etc1.getId(), 2, etc1.getPrice());
            OrderDetailsDTO orderDetailDTO2 = new OrderDetailsDTO(etc2.getId(), 1, etc2.getPrice());

            // 6. Create an OrderDTO
            OrderDTO orderDTO = new OrderDTO(
                    salesperson.getSalespersonId(), 
                    Arrays.asList(orderDetailDTO1, orderDetailDTO2),
                    null, // Order ID will be generated
                    null, null, // Salesperson's first and last name will be set by the service
                    new Date()
            );

            // 7. Create a new order using the OrderService
          //Tämä kommentoitu pois, sillä luo aina uuden tilauksen käynnistyessään
           /*    try {
                OrderDTO createdOrder = orderService.newOrder(orderDTO);
                System.out.println("Created Order: " + createdOrder);
            } catch (DataIntegrityViolationException e) {
                System.out.println("Order already exists or data integrity violation occurred.");
            } 
        */};

    }


}
