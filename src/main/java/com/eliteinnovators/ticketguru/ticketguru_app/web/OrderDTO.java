package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.util.List;

import java.util.Date;


public class OrderDTO {
    private List<OrderDetailsDTO> orderDetails;
    private Long orderId;
    private Long customerId;
    private Long salespersonId;
    private Date orderDate;
    private CustomerDTO customer;
    private SalespersonDTO salesperson;

    public OrderDTO() {}

    public OrderDTO(List<OrderDetailsDTO> orderDetails, Long orderId, Long customerId, Long salespersonId,
            Date orderDate, CustomerDTO customer, SalespersonDTO salesperson) {
        this.orderDetails = orderDetails;
        this.orderId = orderId;
        this.customerId = customerId;
        this.salespersonId = salespersonId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.salesperson = salesperson;
    }
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public Long getSalespersonId() {
        return salespersonId;
    }
    public void setSalespersonId(Long salespersonId) {
        this.salespersonId = salespersonId;
    }

    public List<OrderDetailsDTO> getOrderDetails() {
        return orderDetails;
    }
    public void setOrderDetails(List<OrderDetailsDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    public CustomerDTO getCustomer() {
        return customer;
    }
    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }
    public SalespersonDTO getSalesperson() {
        return salesperson;
    }
    public void setSalesperson(SalespersonDTO salesperson) {
        this.salesperson = salesperson;
    }
}
