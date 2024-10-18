package com.eliteinnovators.ticketguru.ticketguru_app.web;

import jakarta.validation.constraints.*;

public class OrderDetailsDTO {
    @NotNull(message = "OrderDetailsDTO: eventTicketTypeId must not be null")
    private Long eventTicketTypeId;  
    @Min(value = 1, message = "OrderDetailsDTO: Quantity must be greater than 0")
    private int quantity;
    @DecimalMin(value = "0.0", inclusive = false, message = "OrderDetailsDTO: Unit price must be greater than 0")
    private double unitPrice;


    public OrderDetailsDTO() {}

    public OrderDetailsDTO(Long eventTicketTypeId, int quantity, double unitPrice) {
        this.eventTicketTypeId = eventTicketTypeId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getEventTicketTypeId() {
        return eventTicketTypeId;
    }

    public void setEventTicketTypeId(Long eventTicketTypeId) {
        this.eventTicketTypeId = eventTicketTypeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
