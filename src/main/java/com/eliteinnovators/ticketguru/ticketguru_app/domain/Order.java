package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Order {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "salesPerson_id")
    private Salesperson salesperson;

    private Date orderDate;

    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderDetails;


    public Order () {

    }

    public Order(Customer customer, Date orderDate, List<OrderDetails> orderDetails, Long order_id, Salesperson salesperson) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.orderDetails = orderDetails;
        this.order_id = order_id;
        this.salesperson = salesperson;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
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

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Order [order_id=" + order_id + ", customer=" + customer + ", salesperson=" + salesperson
                + ", orderDate=" + orderDate + ", orderDetails=" + orderDetails + "]";
    }
 

}