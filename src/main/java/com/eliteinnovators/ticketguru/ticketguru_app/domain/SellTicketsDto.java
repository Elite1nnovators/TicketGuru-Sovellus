package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SellTicketsDto {

    @NotNull(message = "selectedEventId is required")
    private Long selectedEventId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @NotBlank(message = "ticketType is required")
    private String ticketType;

    public Long getSelectedEventId() {
        return selectedEventId;
    }

    public void setSelectedEventId(Long selectedEventId) {
        this.selectedEventId = selectedEventId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }
}
