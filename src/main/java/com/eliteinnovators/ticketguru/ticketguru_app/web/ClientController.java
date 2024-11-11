package com.eliteinnovators.ticketguru.ticketguru_app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    @GetMapping("/bugivelhot")
    public String showLoginPage() {
        return "login"; // Ohjaa käyttäjän ensin kirjautumissivulle (login.html)
    }

    @GetMapping("/client")
    public String showClientPage() {
        return "client"; // Palauttaa Thymeleaf-näkymän "client.html" templates-kansiosta
    }
}

