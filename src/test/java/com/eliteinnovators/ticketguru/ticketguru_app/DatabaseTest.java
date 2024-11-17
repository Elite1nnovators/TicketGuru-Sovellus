package com.eliteinnovators.ticketguru.ticketguru_app;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.OrderRepository;

@SpringBootTest
public class DatabaseTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testDatabaseConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            assertTrue(connection.isValid(2));
        }
    }

    @Test
    public void testDatabaseConnectionFailure() {
        assertThrows(SQLException.class, () -> {
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://invalid-url")) {
                // Yritä avata yhteys väärään URL:iin
            }
        });
    }

    @Test
    public void testQueryPerformance() {
        long startTime = System.currentTimeMillis();

        List<Order> results = orderRepository.findAll(); // Suorita kysely

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("testQueryPerformance: Query duration: " + duration + " ms");
        assertTrue(duration < 1000); // Tarkista, että kysely suoritetaan alle sekunnissa
    }
}
