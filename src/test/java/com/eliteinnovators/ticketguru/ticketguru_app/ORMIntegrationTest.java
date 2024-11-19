package com.eliteinnovators.ticketguru.ticketguru_app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Salesperson;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.TicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.OrderRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.SalespersonRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketTypeRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ORMIntegrationTest {

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SalespersonRepository salespersonRepository;

    @Test
    public void testTicketTypeEntityMapping() {

        // Entiteetin ja tietokannan välisen mappingin testaus

        TicketType ticketType = new TicketType("VIP");
        
        // Tallennus tietokantaan
        ticketTypeRepository.save(ticketType);

        // Haetaan tietokannasta
        TicketType fetchedTicketType = ticketTypeRepository.findById(ticketType.getId()).orElse(null);

        // Varmistetaan, että tallennus onnistui
        assertNotNull(fetchedTicketType);
        assertEquals(ticketType.getName(), fetchedTicketType.getName());

        // Varmistetaan, että ID ei ole null
        assertNotNull(fetchedTicketType.getId());

    }

}
