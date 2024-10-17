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

    @NotNull(message = "Order: Customer is required for the order")
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference(value = "customer-order")
    private Customer customer;

    @NotNull(message = "Order: Salesperson is required for the order")
    @ManyToOne
    @JoinColumn(name = "salesperson_id")
    @JsonBackReference(value = "salesperson-order")
    private Salesperson salesperson;

    @NotNull(message = "Order: Order date is required")
    @PastOrPresent(message = "Order date cannot be in the future")
    private Date orderDate;

    public Order() {
    }

    public Order(Customer customer, Date orderDate, Salesperson salesperson) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.salesperson = salesperson;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", customer=" + customer + ", salesperson=" + salesperson
                + ", orderDate=" + orderDate;
    }

}
