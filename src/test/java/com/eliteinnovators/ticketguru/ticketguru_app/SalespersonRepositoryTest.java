package com.eliteinnovators.ticketguru.ticketguru_app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Salesperson;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.SalespersonRepository;

@SpringBootTest
public class SalespersonRepositoryTest {

    @Autowired
    SalespersonRepository salespersonRepository;

    @Test
    public void testFindByUsername() {

        // luo salespersonin
        Salesperson salesperson = new Salesperson();
        salesperson.setFirstName("John");
        salesperson.setUsername("johndoe");
        salesperson.setLastName("Doe");
        salesperson.setPasswordHash("securepasswordhash");
        salesperson = salespersonRepository.save(salesperson);

        // Etsii salespersonin käyttäjätunnuksen mukaan
        Optional<Salesperson> found = salespersonRepository.findByUsername(salesperson.getUsername());
        
        // Vahvistaa, että käyttäjätunnuslöytyy
        assertTrue(found.isPresent(), "Salesperson should be found by username");

        // Hakee löydetyn  Salespersonin
        Salesperson foundSalesperson = found.get();

        // Vahvistaa että nämä vastaavat toisiaan
        assertEquals(salesperson.getUsername(), foundSalesperson.getUsername(), "The usernames should match");
    }
}
