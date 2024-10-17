package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    @NotNull(message = "OrderDetails: Order must not be null")
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference(value = "order-orderDetails")
    private Order order;

    @NotNull(message = "OrderDetails: Ticket must not be null")
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    @JsonBackReference(value = "ticket-orderDetails")
    private Ticket ticket;

    @DecimalMin(value = "0.0", inclusive = false, message = "OrderDetails: Unit price must be greater than 0")
    private double unitPrice;

    @Min(value = 1, message = "OrderDetails: Quantity must be greater than 0")
    private int quantity;

    public OrderDetails () {
    }

    public OrderDetails(Order order, int quantity, Ticket ticket, double unitPrice) {
        this.order = order;
        this.quantity = quantity;
        this.ticket = ticket;
        this.unitPrice = unitPrice;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetails [orderDetailId=" + orderDetailId + ", order=" + order + ", ticket=" + ticket
                + ", unitPrice=" + unitPrice + ", quantity=" + quantity + "]";
    }
}
