package com.eliteinnovators.ticketguru.ticketguru_app.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Order {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;

    @OneToMany
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany
    @JoinColumn(name = "salesPerson_id")
    private Salesperson salesperson;

    private Date orderDate;

    // Lisää yhteys orderDetails tauluun
    //@ManyToOne(mappedBy = "order")
    //private List<OrderDetail> orderDetails;


    public Order () {

    }

    public Order(Customer customer, Date orderDate, Long order_id, Salesperson salesperson) {
        this.customer = customer;
        this.orderDate = orderDate;
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

    @Override
    public String toString() {
        return "Order [order_id=" + order_id + ", customer=" + customer + ", salesperson=" + salesperson
                + ", orderDate=" + orderDate + "]";
    }

}