package com.eliteinnovators.ticketguru.ticketguru_app;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testShowLoginPage() throws Exception {
        mockMvc.perform(get("/bugivelhot"))
               .andExpect(status().isOk())
               .andExpect(view().name("login"));
    }

    @Test
    public void testShowClientPage() throws Exception {
        mockMvc.perform(get("/client"))
               .andExpect(status().isOk())
               .andExpect(view().name("client"));
    }
}
