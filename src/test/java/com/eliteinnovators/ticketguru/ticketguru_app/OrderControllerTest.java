package com.eliteinnovators.ticketguru.ticketguru_app;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Salesperson;
import com.eliteinnovators.ticketguru.ticketguru_app.service.OrderService;
import com.eliteinnovators.ticketguru.ticketguru_app.web.OrderDTO;
import com.eliteinnovators.ticketguru.ticketguru_app.web.OrderDetailsDTO;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;


    @Test
    public void testGetAllOrders() throws Exception {
        OrderDTO order1 = new OrderDTO(1L, Arrays.asList(), 1L, "John", "Doe", new Date());
        OrderDTO order2 = new OrderDTO(2L, Arrays.asList(), 2L, "Jane", "Smith", new Date());

        when(orderService.getAllOrders()).thenReturn(Arrays.asList(order1, order2));

        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
            .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].salespersonFirstName").value("John"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].salespersonLastName").value("Smith"));
    }

    @Test
    public void testGetOrderById() throws Exception {
        OrderDTO order = new OrderDTO(1L, Arrays.asList(), 1L, "John", "Doe", new Date());
        when(orderService.getOrderById(1L)).thenReturn(order);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salespersonFirstName").value("John"))
                .andExpect(jsonPath("$.salespersonLastName").value("Doe"));
    }

    @Test
    public void testCreateOrder() throws Exception {
    // Luo mockattu OrderDTO
        OrderDTO mockOrderDTO = new OrderDTO(1L, Arrays.asList(new OrderDetailsDTO(1L, 2, 10.5)), 1L, "John", "Doe", new Date());

        when(orderService.newOrder(Mockito.any(OrderDTO.class))).thenReturn(mockOrderDTO);

        mockMvc.perform(post("/orders")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"salespersonId\":1, \"orderDetails\":[{\"eventTicketTypeId\":1, \"quantity\":2, \"unitPrice\":10.5}] }")) 
                .andExpect(status().isCreated())  // Odotetaan 201 Created
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.salespersonFirstName").value("John"));  
    }

    @Test
    public void testEditOrder() throws Exception {
    // Luo mockattu Order
        Order mockEditedOrder = new Order();
        mockEditedOrder.setOrderId(1L);

        Salesperson mockSalesperson = new Salesperson();
        mockSalesperson.setFirstName("John");
        mockSalesperson.setLastName("UpdatedLastName");
        mockEditedOrder.setSalesperson(mockSalesperson);
        mockEditedOrder.setOrderDate(new Date());

        when(orderService.editOrder(Mockito.any(OrderDTO.class), Mockito.eq(1L))).thenReturn(mockEditedOrder);

        mockMvc.perform(put("/orders/1")
                        .contentType("application/json")
                        .content("{\"salespersonId\":1, \"orderDetails\":[]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salespersonLastName").value("UpdatedLastName"));
}


    @Test
    public void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isNoContent());
    }


    @Test
    public void testPatchOrder() throws Exception {
    // Luo mockattu Order
        Order mockPatchedOrder = new Order();
        mockPatchedOrder.setOrderId(1L);

        Salesperson mockSalesperson = new Salesperson();
        mockSalesperson.setFirstName("John");
        mockSalesperson.setLastName("Doe");
        mockPatchedOrder.setSalesperson(mockSalesperson);
        mockPatchedOrder.setOrderDate(new Date());

        when(orderService.patchOrder(Mockito.any(OrderDTO.class), Mockito.eq(1L))).thenReturn(mockPatchedOrder);

        mockMvc.perform(patch("/orders/1")
                        .contentType("application/json")
                        .content("{\"salespersonFirstName\":\"John\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salespersonFirstName").value("John"));
}
}
