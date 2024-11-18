package com.eliteinnovators.ticketguru.ticketguru_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;
import com.eliteinnovators.ticketguru.ticketguru_app.service.TicketService;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;



@SpringBootTest
@AutoConfigureMockMvc
public class RestApiTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;


    @Test
    public void testGetEventByIdRoute() throws Exception {
        mockMvc.perform(get("/events/1")) 
            .andExpect(status().isOk())  // Tarkistetaan, että status on 200 OK
            .andExpect(handler().methodName("getEventById"))  // Tarkistetaan, että reitti menee oikeaan metodiin
            .andExpect(jsonPath("$.eventId").value(1));  
    }

    @Test
    public void testGetOrdersRoute() throws Exception {
        mockMvc.perform(get("/orders")  // Pyydetään /orders-reittiä
            .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
            .andExpect(status().isOk())  // Tarkistetaan, että status on 200 OK
            .andExpect(handler().methodName("getAllOrders"))  // Tarkistetaan, että reitti menee oikeaan metodiin
            .andExpect(jsonPath("$").isArray());  // Tarkistetaan, että vastauksena lista tilauksista
}


    @Test
    public void testInvalidRoute() throws Exception {
        mockMvc.perform(get("/invalid-route"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testGetTicketsEventStatus() throws Exception {

        // Mockataan lippuja:
        List<Ticket> mockTickets = Arrays.asList(
            new Ticket(null, "TICKET123", true),
            new Ticket(null, "TICKET456", false)
        );
        
        // Määritellään, että mockattu palvelu palauttaa yllä olevat liput tapahtumalle 1
        when(ticketService.getTicketsByEvent(1L)).thenReturn(mockTickets);

        mockMvc.perform(get("/tickets/event/1")) 
           .andExpect(status().isOk())  // Tarkistetaan, että palautuu 200 OK
           .andExpect(jsonPath("$").isArray())
           .andExpect(jsonPath("$[0].ticketCode").exists())
           .andExpect(jsonPath("$[0].valid").exists());
    }

    @Test
    public void testCreateOrderRoute() throws Exception {
        String orderJson = "{\"salespersonId\": 1, \"orderDetails\": [{\"eventTicketTypeId\": 2, \"quantity\": 1, \"unitPrice\": 15}]}";

        mockMvc.perform(post("/orders")
            .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes()))
            .content(orderJson)  // Lähetetään JSON-muotoinen tilauksen tiedot
            .contentType(MediaType.APPLICATION_JSON))  // Määritellään, että sisältö on JSON
            .andExpect(status().isCreated())  // palauttaa 201 Created 
            .andExpect(handler().methodName("newOrder")); 
}

    
}

    



    




    




