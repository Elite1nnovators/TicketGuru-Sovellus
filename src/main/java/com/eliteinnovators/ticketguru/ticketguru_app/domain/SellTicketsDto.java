package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SellTicketsDto {

    @NotNull(message = "selectedEventId is required")
    private Long selectedEventId;

    @NotNull(message = "isSoldAtDoor is required")
    private boolean isSoldAtDoor;

    @NotEmpty(message = "At least one ticket selection is required")
    private List<TicketSelection> ticketSelections;

    // Inner DTO for handling multiple ticketTypes within an order
    public static class TicketSelection {
        @NotBlank(message = "ticketType is required")
        private String ticketType;

        @Min(value = 1, message = "Quantity must be at least 1")
        private int quantity;

        public String getTicketType() {
            return ticketType;
        }
        public void setTicketType(String ticketType) {
            this.ticketType = ticketType;
        }
        public int getQuantity() {
            return quantity;
        }
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public List<TicketSelection> getTicketSelections() {
        return ticketSelections;
    }

    public void setTicketSelections(List<TicketSelection> ticketSelections) {
        this.ticketSelections = ticketSelections;
    }

    public boolean isSoldAtDoor() {
        return isSoldAtDoor;
    }

    public void setSoldAtDoor(boolean isSoldAtDoor) {
        this.isSoldAtDoor = isSoldAtDoor;
    }

    public Long getSelectedEventId() {
        return selectedEventId;
    }

    public void setSelectedEventId(Long selectedEventId) {
        this.selectedEventId = selectedEventId;
    }
}
