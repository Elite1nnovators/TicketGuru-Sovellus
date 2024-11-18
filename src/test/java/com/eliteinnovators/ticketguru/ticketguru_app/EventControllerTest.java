package com.eliteinnovators.ticketguru.ticketguru_app;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.service.EventService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private EventService eventService;

        private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        @Test
        public void testGetAllEvents() throws Exception {
                when(eventService.getAllEvents()).thenReturn(Arrays.asList(
                                new Event("Concert", new Date(), "Venue 1", "Helsinki", "Music Event"),
                                new Event("Theatre", new Date(), "Venue 2", "Espoo", "Drama Play")));

                mockMvc.perform(get("/events"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].eventName").value("Concert"))
                                .andExpect(jsonPath("$[0].eventCity").value("Helsinki"))
                                .andExpect(jsonPath("$[1].eventName").value("Theatre"))
                                .andExpect(jsonPath("$[1].eventCity").value("Espoo"));
        }

        @Test
        public void testGetEventById() throws Exception {
                Event event = new Event("Concert", new Date(), "Venue 1", "Helsinki", "Music Event");
                when(eventService.getEventById(1L)).thenReturn(event);

                mockMvc.perform(get("/events/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.eventName").value("Concert"))
                                .andExpect(jsonPath("$.eventCity").value("Helsinki"))
                                .andExpect(jsonPath("$.eventAddress").value("Venue 1"));
        }

        @Test
        public void testCreateEvent() throws Exception {
                Event newEvent = new Event(
                                "New Event",
                                dateFormat.parse("2024-06-01"),
                                "456 Avenue",
                                "New City",
                                "A new event description");
                newEvent.setEventId(2L);

                when(eventService.createNewEvent(Mockito.any())).thenReturn(newEvent);

                mockMvc.perform(post("/events")
                                .contentType("application/json")
                                .content("{ \"eventName\": \"New Event\", " +
                                                "\"eventDate\": \"2024-06-01\", " +
                                                "\"eventAddress\": \"456 Avenue\", " +
                                                "\"eventCity\": \"New City\", " +
                                                "\"eventDescription\": \"A new event description\" }"))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.eventId").value(2))
                                .andExpect(jsonPath("$.eventName").value("New Event"))
                                .andExpect(jsonPath("$.eventDate").value("2024-05-31T21:00:00.000+00:00"))
                                .andExpect(jsonPath("$.eventAddress").value("456 Avenue"))
                                .andExpect(jsonPath("$.eventCity").value("New City"))
                                .andExpect(jsonPath("$.eventDescription").value("A new event description"));
        }

        @Test
        public void testCreateEventValidationFailure() throws Exception {
                mockMvc.perform(post("/events")
                                .contentType("application/json")
                                .content("{\n" +
                                                "    \"eventDate\": \"2024-10-01T05:08:30.651+00:00\",\n" +
                                                "    \"eventAddress\": \"Event Address 1\",\n" +
                                                "    \"eventCity\": \"Helsinki\",\n" +
                                                "    \"eventDescription\": \"A great concert event\",\n" +
                                                "    \"eventTicketTypes\": [\n" +
                                                "        {\n" +
                                                "            \"ticketType\": {\n" +
                                                "                \"id\": 1,\n" +
                                                "                \"name\": \"Aikuinen\"\n" +
                                                "            },\n" +
                                                "            \"price\": 10,\n" +
                                                "            \"ticketsInStock\": 40\n" +
                                                "        }\n" +
                                                "    ]\n" +
                                                "}")) // Missing "eventName"
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$[0].field").value("eventName"))
                                .andExpect(jsonPath("$[0].defaultMessage").value("Event name is mandatory"));
        }

        @Test
        public void testDeleteEvent() throws Exception {
                mockMvc.perform(delete("/events/1"))
                                .andExpect(status().isNoContent()); // Assuming deletion returns HTTP 204
        }

        // Lisään put, search ja patch testauksen

        @Test
        public void testPatchEvent() throws Exception {
                // Existing event to be patched
                Event originalEvent = new Event("Concert", new Date(), "Venue 1", "Helsinki", "Music Event");
                originalEvent.setEventId(1L);

                // Partial update: change the event name and city
                Event patchedEvent = new Event("Updated Concert", originalEvent.getEventDate(),
                                originalEvent.getEventAddress(), "Updated City", originalEvent.getEventDescription());
                patchedEvent.setEventId(1L);

                // Mock the patching behavior in the service layer
                when(eventService.patchEvent(1L, Map.of("eventName", "Updated Concert", "eventCity", "Updated City")))
                                .thenReturn(patchedEvent);

                // Perform the PATCH request and verify the results
                mockMvc.perform(patch("/events/1")
                                .contentType("application/json")
                                .content("{ \"eventName\": \"Updated Concert\", \"eventCity\": \"Updated City\" }"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.eventName").value("Updated Concert"))
                                .andExpect(jsonPath("$.eventCity").value("Updated City"))
                                .andExpect(jsonPath("$.eventAddress").value("Venue 1"))
                                .andExpect(jsonPath("$.eventDescription").value("Music Event"));
        }

        @Test
        public void testPutEvent() throws Exception {
    
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fixedDate = dateFormat.parse("2024-06-01");
        

            Event originalEvent = new Event("Concert", fixedDate, "Venue 1", "Helsinki", "Music Event");
            originalEvent.setEventId(1L);
        
    
            Event updatedEvent = new Event("Updated Concert", fixedDate, "New Venue", "Updated City", "Updated Music Event");
            updatedEvent.setEventId(1L);
        

            when(eventService.editEvent(Mockito.any(Event.class), Mockito.eq(1L))).thenReturn(updatedEvent);
       
            mockMvc.perform(put("/events/1")
                            .contentType("application/json")
                            .content("{ \"eventId\": 1, " +
                                     "\"eventName\": \"Updated Concert\", " +
                                     "\"eventDate\": \"2024-06-01\", " +
                                     "\"eventAddress\": \"New Venue\", " +
                                     "\"eventCity\": \"Updated City\", " +
                                     "\"eventDescription\": \"Updated Music Event\" }"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.eventId").value(1))
                    .andExpect(jsonPath("$.eventName").value("Updated Concert"))
                    .andExpect(jsonPath("$.eventDate").value("2024-05-31T21:00:00.000+00:00"))  //Huomioitu aikaero
                    .andExpect(jsonPath("$.eventAddress").value("New Venue"))
                    .andExpect(jsonPath("$.eventCity").value("Updated City"))
                    .andExpect(jsonPath("$.eventDescription").value("Updated Music Event"));
        }

@Test
public void testSearchEventsByCity() throws Exception {
    // Mock events for Helsinki and Espoo
    Event event1 = new Event("Concert", new Date(), "Venue 1", "Helsinki", "Music Event");
    Event event2 = new Event("Theatre", new Date(), "Venue 2", "Espoo", "Drama Play");

    // Mock the service layer to return events for Helsinki
    when(eventService.searchEventsByCity("Helsinki")).thenReturn(Arrays.asList(event1));

    // Perform the GET request to search for events in Helsinki
    mockMvc.perform(get("/events/search?eventCity=Helsinki"))
            .andExpect(status().isOk())  // Expecting HTTP status 200
            .andExpect(jsonPath("$[0].eventCity").value("Helsinki"))
            .andExpect(jsonPath("$[0].eventName").value("Concert"));
}

@Test
public void testSearchEventsByCityNoResults() throws Exception {
    // Mock the service layer to return an empty list for a city with no events
    when(eventService.searchEventsByCity("NonExistentCity")).thenReturn(Arrays.asList());

    // Perform the GET request to search for events in a non-existent city
    mockMvc.perform(get("/events/search?eventCity=NonExistentCity"))
            .andExpect(status().isNotFound())  // Expecting HTTP status 404 when no events are found
            .andExpect(content().string("[]"));  // Expecting an empty list in the response
}

@Test
public void testSearchAllEventsWhenNoCityProvided() throws Exception {
    // Mock the service layer to return all events
    Event event1 = new Event("Concert", new Date(), "Venue 1", "Helsinki", "Music Event");
    Event event2 = new Event("Theatre", new Date(), "Venue 2", "Espoo", "Drama Play");
    when(eventService.getAllEvents()).thenReturn(Arrays.asList(event1, event2));

    // Perform the GET request to search for all events (no city parameter)
    mockMvc.perform(get("/events/search"))
            .andExpect(status().isOk())  // Expecting HTTP status 200
            .andExpect(jsonPath("$[0].eventCity").value("Helsinki"))
            .andExpect(jsonPath("$[0].eventName").value("Concert"))
            .andExpect(jsonPath("$[1].eventCity").value("Espoo"))
            .andExpect(jsonPath("$[1].eventName").value("Theatre"));
        }

}
