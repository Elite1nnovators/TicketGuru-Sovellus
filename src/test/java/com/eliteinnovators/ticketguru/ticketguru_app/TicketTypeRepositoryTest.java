package com.eliteinnovators.ticketguru.ticketguru_app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.TicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketTypeRepository;

@SpringBootTest
public class TicketTypeRepositoryTest {


    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    private TicketType ticketType;

    @BeforeEach
    public void setUp() {
        ticketType = new TicketType();
        ticketType.setName("UUSI");
        ticketTypeRepository.save(ticketType);
    }


    // En aivan ymmärrä miksi joka toisella kerralla tämä toimii ja joka toisella ei?
    @Test
    public void testFindByName_Success() {
        Optional<TicketType> foundTicketType = ticketTypeRepository.findByName("UUSI");
        assertTrue(foundTicketType.isPresent(), "TicketType should be found");
        assertEquals("UUSI", foundTicketType.get().getName(), "The name of the found TicketType should be 'UUSI'");
    }

    @Test
    public void testFindByName_NotFound() {
        Optional<TicketType> foundTicketType = ticketTypeRepository.findByName("NonExistentName");
        assertFalse(foundTicketType.isPresent(), "TicketType should not be found");
    }

    @Test
    public void testSaveTicketType() {
        TicketType newTicketType = new TicketType();
        newTicketType.setName("Standard");

        TicketType savedTicketType = ticketTypeRepository.save(newTicketType);
        assertNotNull(savedTicketType.getId(), "TicketType should have a generated ID after saving");
        assertEquals("Standard", savedTicketType.getName(), "The name of the saved TicketType should be 'Standard'");
    }

}
