package com.eliteinnovators.ticketguru.ticketguru_app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientIndexTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testIndexPageContainsWelcomeText() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk()) // status on 200 OK
                .andExpect(content().string(containsString("Welcome to TicketGuru!")));
    }

    @Test
    public void testIndexPageIsNotEmpty() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk()) // status on 200 OK
                .andExpect(content().string(is(not("")))); // Varmistaa, että sivun sisältö ei ole tyhjä
    }
}

