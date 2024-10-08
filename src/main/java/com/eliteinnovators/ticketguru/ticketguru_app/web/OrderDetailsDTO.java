package com.eliteinnovators.ticketguru.ticketguru_app.web;

public class OrderDetailsDTO {
    private Long ticketId;  
    private int quantity;
    private double unitPrice;

    public OrderDetailsDTO() {}

    public OrderDetailsDTO(Long ticketId, int quantity, double unitPrice) {
        this.ticketId = ticketId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
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
