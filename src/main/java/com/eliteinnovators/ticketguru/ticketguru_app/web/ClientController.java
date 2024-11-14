package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.service.EventService;
import com.eliteinnovators.ticketguru.ticketguru_app.service.TicketService;

@Controller
public class ClientController {

    @Autowired
    private EventService eventService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/bugivelhot")
    public String showLoginPage() {
        return "login"; // Ohjaa käyttäjän ensin kirjautumissivulle (login.html)
    }

    @GetMapping("/client")
    public String showClientPage() {
        return "client"; // Palauttaa Thymeleaf-näkymän "client.html" templates-kansiosta
    }

    // TicketDashboard



    @GetMapping("/ticketdashboard") // Mahdollistetaan lippujen vieminen sivulle
    public String getEvents(Model model) {
        List<Event> allEvents = eventService.getAllEvents();
        model.addAttribute("events", allEvents);

        return "ticketdashboard";
    }

    @PostMapping("/sell") // Ostolomakkeen (ticketdashboard.html) metodi
    public String purchaseTickets(@RequestParam Long selectedEventId, @RequestParam int quantity, @RequestParam String ticketType,
             RedirectAttributes redirectAttributes) {
        Event selectedEvent = eventService.getEventById(selectedEventId);
        

        // Tarkistetaan täyttyvätkö vähimmäisehdot lipun myymiselle
        if (selectedEvent == null || quantity <= 0) { 
            redirectAttributes.addFlashAttribute("error", "Invalid event or quantity.");
            return "ticketdashboard";
        }

        

        // Valitun lipun/lippujen muutos (quantity/valid)
        int ticketTypeIndex = "VIP".equalsIgnoreCase(ticketType) ? 0 : 1;

        if (selectedEvent.getEventTicketTypes().size() <= ticketTypeIndex) {
            redirectAttributes.addFlashAttribute("error", "Invalid ticket type selection.");
            return "redirect:/ticketdashboard";
        }

        // Loppuhinnan laskeminen
        int price = (ticketTypeIndex == 0) ? 20 : 15;
        int total = quantity * price;


        // Lipputyyppien perusteella lippujen myyminen (TicketsInStock: -quantity, Valid: TRUE)
        EventTicketType selectedTicketType = selectedEvent.getEventTicketTypes().get(ticketTypeIndex);
    
        int availableTickets = selectedTicketType.getTicketsInStock();
        if (availableTickets >= quantity) {
            selectedTicketType.setTicketsInStock(availableTickets - quantity);
    
            List<Ticket> tickets = selectedTicketType.getTickets().stream()
                    .filter(Ticket::isValid)
                    .limit(quantity)
                    .collect(Collectors.toList());

                tickets.forEach(ticket -> ticket.setValid(true));

                ticketRepository.saveAll(tickets);
                eventService.editEvent(selectedEvent, selectedEventId);
                redirectAttributes.addFlashAttribute("success", "Tickets sold successfully! Total price: " + total + ".00 €"); 
                return "redirect:/ticketdashboard";
            }
        

        redirectAttributes.addFlashAttribute("error", "Not enough tickets available.");
        return "redirect:/ticketdashboard";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/index")
    public String getIndexPage() {
        return "index";
    }

}
