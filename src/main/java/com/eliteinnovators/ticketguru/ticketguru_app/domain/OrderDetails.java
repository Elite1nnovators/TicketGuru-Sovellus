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

    @NotNull(message = "OrderDetails: EventTicketType must not be null")
    @ManyToOne
    @JoinColumn(name = "eventTicketType_id")
    private EventTicketType eventTicketType;

    @DecimalMin(value = "0.0", inclusive = false, message = "OrderDetails: Unit price must be greater than 0")
    private double unitPrice;

    @Min(value = 1, message = "OrderDetails: Quantity must be greater than 0")
    private int quantity;

    public OrderDetails () {}
    public OrderDetails(Order order, EventTicketType eventTicketType, int quantity, double unitPrice) {
        this.order = order;
        this.eventTicketType = eventTicketType;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    public EventTicketType getEventTicketType() {
        return eventTicketType;
    }
    public void setEventTicketType(EventTicketType eventTicketType) {
        this.eventTicketType = eventTicketType;
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
}
