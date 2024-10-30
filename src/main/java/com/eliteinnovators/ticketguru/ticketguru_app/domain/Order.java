package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import java.util.*;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "order_entity")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @NotEmpty(message = "Order: Order must have at least one order detail")
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "order-orderDetails")
    private List<OrderDetails> orderDetails = new ArrayList<>(); 

    @NotNull(message = "Order: Salesperson is required for the order")
    @ManyToOne
    @JoinColumn(name = "salesperson_id")
    @JsonBackReference(value = "salesperson-order")
    private Salesperson salesperson;

    @NotNull(message = "Order: Order date is required")
    @PastOrPresent(message = "Order date cannot be in the future")
    private Date orderDate;

    @NotEmpty(message = "Order: Tickets must contain at least one ticket")
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    public Order() {}
    public Order(Salesperson salesperson, Date orderDate) {
        this.salesperson = salesperson;
        this.orderDate = orderDate;
    }
    public Order(Salesperson salesperson, Date orderDate, List<Ticket> tickets) {
        this.salesperson = salesperson;
        this.orderDate = orderDate;
        this.tickets = tickets;
        for (Ticket ticket : tickets) {
            ticket.setOrder(this);
        }
    }
    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
        for (OrderDetails orderDetail : orderDetails) {
            orderDetail.setOrder(this);
        }
    }
    public List<Ticket> getTickets() {
        return tickets;
    }
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Salesperson getSalesperson() {
        return salesperson;
    }
    public void setSalesperson(Salesperson salesperson) {
        this.salesperson = salesperson;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
