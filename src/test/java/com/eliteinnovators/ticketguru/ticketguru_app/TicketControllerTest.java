package com.eliteinnovators.ticketguru.ticketguru_app;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;
import com.eliteinnovators.ticketguru.ticketguru_app.service.TicketService;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    
    @Test
    public void testGetAllTickets() throws Exception {

        EventTicketType mockEventTicketType = Mockito.mock(EventTicketType.class);
        when(mockEventTicketType.getId()).thenReturn(1L);

        Order mockOrder = Mockito.mock(Order.class);
        when(mockOrder.getOrderId()).thenReturn(1L);

        // Luo mock-liput
        Ticket ticket1 = new Ticket(mockEventTicketType, mockOrder, "Code1", true);
        Ticket ticket2 = new Ticket(mockEventTicketType, mockOrder, "Code2", false);
        when(ticketService.getAllTickets()).thenReturn(Arrays.asList(ticket1, ticket2));

        mockMvc.perform(get("/tickets")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ticketCode").value("Code1"))
                .andExpect(jsonPath("$[1].ticketCode").value("Code2"));
}


    @Test
    public void testGetTicketById() throws Exception {
        
        EventTicketType eventTicketType = new EventTicketType();
        eventTicketType.setId(1L);

        Order order = new Order();
        order.setOrderId(1L);

        // Luo mock-lippu
        Ticket ticket = new Ticket(eventTicketType, order, "Code1", true);
        when(ticketService.getTicketById(1L)).thenReturn(ticket);

        mockMvc.perform(get("/tickets/1")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketCode").value("Code1"))
                .andExpect(jsonPath("$.valid").value(true));
    }

    @Test
    public void testGetTicketsByEventAndTicketCode() throws Exception {
       
        EventTicketType eventTicketType = new EventTicketType();
        eventTicketType.setId(1L);

        Ticket ticket = new Ticket(eventTicketType, null, "Code1", true);
        when(ticketService.getTicketByEventAndCode(1L, "Code1")).thenReturn(ticket);

        mockMvc.perform(get("/tickets/event/1")
                .param("ticketCode", "Code1")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketCode").value("Code1"))
                .andExpect(jsonPath("$.valid").value(true));
    }

    @Test
    public void testGetTicketsByEvent() throws Exception {
        EventTicketType eventTicketType = new EventTicketType();
        eventTicketType.setId(1L);

        Ticket ticket1 = new Ticket(eventTicketType, null, "Code1", true);
        Ticket ticket2 = new Ticket(eventTicketType, null, "Code2", false);

        when(ticketService.getTicketsByEvent(1L)).thenReturn(Arrays.asList(ticket1, ticket2));

        mockMvc.perform(get("/tickets/event/1")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ticketCode").value("Code1"))
                .andExpect(jsonPath("$[1].ticketCode").value("Code2"));
    }

    @Test
    public void testGetTicketsByOrder() throws Exception {

        EventTicketType eventTicketType = new EventTicketType();
        eventTicketType.setId(1L);

        Ticket ticket1 = new Ticket(eventTicketType, null, "Code1", true);
        Ticket ticket2 = new Ticket(eventTicketType, null, "Code2", false);

        when(ticketService.getTicketsByOrder(1L)).thenReturn(Arrays.asList(ticket1, ticket2));

        mockMvc.perform(get("/tickets/order/1")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ticketCode").value("Code1"))
                .andExpect(jsonPath("$[1].ticketCode").value("Code2"));
    }

    @Test
    public void testPatchTicketByTicketCode() throws Exception {

        EventTicketType eventTicketType = new EventTicketType();
        eventTicketType.setId(1L);

        Ticket patchedTicket = new Ticket(eventTicketType, null, "CodeUpdated", false);

        when(ticketService.patchTicketByTicketCode("Code1", 1L, Map.of("ticketCode", "CodeUpdated")))
                .thenReturn(patchedTicket);

        mockMvc.perform(patch("/tickets/event/1")
                .param("ticketCode", "Code1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ticketCode\":\"CodeUpdated\"}")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketCode").value("CodeUpdated"))
                .andExpect(jsonPath("$.valid").value(false));
    }

}
