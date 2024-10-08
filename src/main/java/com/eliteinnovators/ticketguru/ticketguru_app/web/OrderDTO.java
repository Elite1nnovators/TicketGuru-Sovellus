package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.util.List;
import java.util.Date;


public class OrderDTO {
    private Long orderId;
    private Long customerId;
    private String customerFirstName;
    private String customerLastName;
    private Long salespersonId;
    private String salespersonFirstName;
    private String salespersonLastName;
    private Date orderDate;
    private List<OrderDetailsDTO> orderDetails;
    public OrderDTO() {
    }
    public OrderDTO(Long orderId, Long customerId, String customerFirstName, String customerLastName,
            Long salespersonId, String salespersonFirstName, String salespersonLastName, Date orderDate,
            List<OrderDetailsDTO> orderDetails) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.salespersonId = salespersonId;
        this.salespersonFirstName = salespersonFirstName;
        this.salespersonLastName = salespersonLastName;
        this.orderDate = orderDate;
        this.orderDetails = orderDetails;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public String getCustomerFirstName() {
        return customerFirstName;
    }
    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }
    public String getCustomerLastName() {
        return customerLastName;
    }
    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }
    public Long getSalespersonId() {
        return salespersonId;
    }
    public void setSalespersonId(Long salespersonId) {
        this.salespersonId = salespersonId;
    }
    public String getSalespersonFirstName() {
        return salespersonFirstName;
    }
    public void setSalespersonFirstName(String salespersonFirstName) {
        this.salespersonFirstName = salespersonFirstName;
    }
    public String getSalespersonLastName() {
        return salespersonLastName;
    }
    public void setSalespersonLastName(String salespersonLastName) {
        this.salespersonLastName = salespersonLastName;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    public List<OrderDetailsDTO> getOrderDetails() {
        return orderDetails;
    }
    public void setOrderDetails(List<OrderDetailsDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
