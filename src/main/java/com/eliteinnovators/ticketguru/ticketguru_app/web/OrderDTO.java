package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

// Toimii request ja response entitynä
//response == tieto palautetaan responsena clientin pyyntöön
public class OrderDTO {

    @NotNull(message = "OrderDTO: Salesperson ID is required")
    private Long salespersonId;
    @NotEmpty(message = "OrderDTO: Order must contain at least one order detail")
    private List<@Valid OrderDetailsDTO> orderDetails;

    private Long orderId; //response
    private String salespersonFirstName; //response
    private String salespersonLastName; //response
    private Date orderDate; //response
    
    public OrderDTO(
            @NotNull(message = "OrderDTO: Salesperson ID is required") Long salespersonId,
            @NotEmpty(message = "OrderDTO: Order must contain at least one order detail") List<@Valid OrderDetailsDTO> orderDetails,
            Long orderId, String salespersonFirstName,
            String salespersonLastName, Date orderDate) {
        this.salespersonId = salespersonId;
        this.orderDetails = orderDetails;
        this.orderId = orderId;
        this.salespersonFirstName = salespersonFirstName;
        this.salespersonLastName = salespersonLastName;
        this.orderDate = orderDate;
    }
    public OrderDTO() {}
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
