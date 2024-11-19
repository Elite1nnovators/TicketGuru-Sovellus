package com.eliteinnovators.ticketguru.ticketguru_app.web;

<<<<<<< HEAD

import java.util.List;

=======
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
>>>>>>> e4f636b8695bead731a67702ef591e9e53ccf3b3

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
<<<<<<< HEAD

=======
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
>>>>>>> e4f636b8695bead731a67702ef591e9e53ccf3b3
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Event;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.EventTicketType;
<<<<<<< HEAD

import com.eliteinnovators.ticketguru.ticketguru_app.exception.InsufficientTicketsException;

import com.eliteinnovators.ticketguru.ticketguru_app.service.EventService;
import com.eliteinnovators.ticketguru.ticketguru_app.service.OrderService;

=======
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.service.EventService;
import com.eliteinnovators.ticketguru.ticketguru_app.service.TicketService;
>>>>>>> e4f636b8695bead731a67702ef591e9e53ccf3b3

@Controller
public class ClientController {

    @Autowired
    private EventService eventService;

    @Autowired
<<<<<<< HEAD
    private OrderService orderService;

  
=======
    private TicketService ticketService;

    @Autowired
    private TicketRepository ticketRepository;
>>>>>>> e4f636b8695bead731a67702ef591e9e53ccf3b3

    @GetMapping("/bugivelhot")
    public String showLoginPage() {
        return "login"; // Ohjaa käyttäjän ensin kirjautumissivulle (login.html)
    }

    @GetMapping("/client")
    public String showClientPage() {
        return "client"; // Palauttaa Thymeleaf-näkymän "client.html" templates-kansiosta
    }
<<<<<<< HEAD

    // TicketDashboard

    @GetMapping("/ticketdashboard") // Mahdollistetaan lippujen vieminen sivulle
    public String getEvents(Model model) {
        List<Event> allEvents = eventService.getAllEvents();
        model.addAttribute("events", allEvents);

        return "ticketdashboard";
    }

    @PostMapping("/sell")
    public String sellTickets(
        @RequestParam Long selectedEventId,
        @RequestParam int quantity,
        @RequestParam String ticketType,
        RedirectAttributes redirectAttributes) {

    Event selectedEvent = eventService.getEventById(selectedEventId);

    if (selectedEvent == null || quantity <= 0) {
        redirectAttributes.addFlashAttribute("error", "Invalid event or quantity.");
        return "redirect:/ticketdashboard";
    }

    int ticketTypeIndex = "VIP".equalsIgnoreCase(ticketType) ? 0 : 1;

    if (selectedEvent.getEventTicketTypes().size() <= ticketTypeIndex) {
        redirectAttributes.addFlashAttribute("error", "Invalid ticket type selection.");
        return "redirect:/ticketdashboard";
    }

    EventTicketType selectedTicketType = selectedEvent.getEventTicketTypes().get(ticketTypeIndex);

    int availableTickets = selectedTicketType.getTicketsInStock();
    if (availableTickets < quantity) {
        redirectAttributes.addFlashAttribute("error", "Not enough tickets available.");
        return "redirect:/ticketdashboard";
    }

    selectedTicketType.setTicketsInStock(availableTickets - quantity);


    OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
    orderDetailsDTO.setEventTicketTypeId(selectedTicketType.getId());
    orderDetailsDTO.setQuantity(quantity);

    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setSalespersonId(1L); 
    orderDTO.setOrderDetails(List.of(orderDetailsDTO)); 

    try {
        OrderDTO savedOrder = orderService.newOrder(orderDTO); 
        redirectAttributes.addFlashAttribute("success", quantity + " " + ticketType + " tickets were sold successfully! Order ID: " + savedOrder.getOrderId());
    } catch (InsufficientTicketsException e) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/ticketdashboard";
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "An error occurred while creating the order.");
        return "redirect:/ticketdashboard";
    }

    return "redirect:/ticketdashboard";
}

=======

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

>>>>>>> e4f636b8695bead731a67702ef591e9e53ccf3b3
    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/index")
    public String getIndexPage() {
        return "index";
    }

}
