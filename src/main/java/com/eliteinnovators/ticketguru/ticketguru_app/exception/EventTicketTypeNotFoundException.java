package com.eliteinnovators.ticketguru.ticketguru_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EventTicketTypeNotFoundException extends RuntimeException {
    public EventTicketTypeNotFoundException(String message) {
        super(message);
    }
}