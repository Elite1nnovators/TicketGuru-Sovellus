package com.eliteinnovators.ticketguru.ticketguru_app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TicketGuruController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
