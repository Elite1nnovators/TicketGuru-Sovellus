package com.eliteinnovators.ticketguru.ticketguru_app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.TicketType;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketTypeRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/tickettypes")
public class TicketTypeController {

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @PostMapping
    public TicketType createTicketType(@RequestBody TicketType ticketType) {
        return ticketTypeRepository.save(ticketType);
    }

}
