package com.eliteinnovators.ticketguru.ticketguru_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OrderDetailsNotFoundException extends RuntimeException {
    public OrderDetailsNotFoundException(String message) {
        super(message);
    }
}
