package com.eliteinnovators.ticketguru.ticketguru_app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.EventTicketTypeRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.OrderRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketRepository;

import jakarta.persistence.EntityManagerFactory;

@SpringBootTest
public class ORMPerformanceTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    
    @Autowired
    private EventTicketTypeRepository eventTicketTypeRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void testEventTicketTypePerformance() {
        long startTime = System.currentTimeMillis();

        // Suoritetaan kysely
        List<EventTicketType> eventTicketTypes = eventTicketTypeRepository.findAll();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Tarkastetaan, että kysely tapahtuu 0,1 sekunnissa
        assertTrue(duration < 100, "Kysely kesti liian kauan: " + duration + " ms");
    }

    @Test
    public void testEventPerformance() {
        long startTime = System.currentTimeMillis();

        List<Event> events = eventRepository.findAll();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Tarkastetaan, että kysely tapahtuu 0,1 sekunnissa
        assertTrue(duration < 100, "Kysely kesti liian kauan: " + duration + " ms");
    }

    @Test
    public void testOrderWithHibernateStatistics() {

        //Haetaan SessionFactory 
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);

        // Lisätään hibernate statistics
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Order> orders = orderRepository.findAll();

        // Haetaan kyselyiden määrä
        long queryCount = sessionFactory.getStatistics().getQueryExecutionCount();

        // Tarkastetaan, että kyselyiden määrä on pienempi kuin 2
        assertTrue(queryCount < 2, "Kyselyiden määrä on liian suuri: " + queryCount);
    }

    @Test
    public void testTicketPerformanceWithHibernateStatistics() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);

        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Ticket> tickets = ticketRepository.findAll();

        long queryCount = sessionFactory.getStatistics().getQueryExecutionCount();

        assertTrue(queryCount < 2, "Kyselyiden määrä on liian suuri: " + queryCount);
    }
    

}
