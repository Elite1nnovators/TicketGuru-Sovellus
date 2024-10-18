package com.eliteinnovators.ticketguru.ticketguru_app.web;

public class OrderDetailsDTO {
    private Long eventTicketTypeId;  
    private int quantity;
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
