package com.eliteinnovators.ticketguru.ticketguru_app;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Base64;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientTicketdashboardTest {

    @Autowired
    private MockMvc mockMvc;

   
    @Test
    public void testTicketdashboardLoads() throws Exception {
        String responseContent = mockMvc.perform(get("/ticketdashboard")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn().getResponse().getContentAsString();

        assertTrue(responseContent.contains("Sell tickets"));
        assertTrue(responseContent.contains("Event Name"));
        assertTrue(responseContent.contains("Ticket Quantity"));
        assertTrue(responseContent.contains("Select the ticket Type"));
    }

    @Test
    public void testTicketDashboardPageContainsSellButton() throws Exception {
        mockMvc.perform(get("/ticketdashboard")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
                .andExpect(status().isOk()) // Varmistaa, että status on 200
                .andExpect(content().string(Matchers.containsString("SELL")));
    }

    @Test
    public void testTicketsSoldSuccessfully() throws Exception {
        // Suoritetaan POST-pyyntö
        mockMvc.perform(post("/sell")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes()))
                .param("selectedEventId", "1")  
                .param("quantity", "5")  
                .param("ticketType", "VIP")) 
                .andExpect(status().is3xxRedirection())  
                .andExpect(redirectedUrl("/ticketdashboard"))  
                .andExpect(flash().attributeExists("success"));  // Varmistetaan, että myynti on onnistunut
    }

    @Test
    public void testNotEnoughTickets() throws Exception {
        // Testaa, jos lippuja ei tarpeeksi jäljellä, näytetään virheviesti
        mockMvc.perform(post("/sell")
                .param("selectedEventId", "1")  
                .param("quantity", "999")  // Ostetaan liikaa lippuja
                .param("ticketType", "VIP")  
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
                .andExpect(status().is3xxRedirection())  
                .andExpect(redirectedUrl("/ticketdashboard"))  // Ohjaa takaisin ticketdashboardiin
                .andExpect(flash().attributeExists("error"))  
                .andExpect(flash().attribute("error", "Not enough tickets available.")); 
    }


}
   
