package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.util.List;

public class OrderDTO {
    private Long customerId;
    private Long salespersonId;
    private List<OrderDetailsDTO> orderDetails;

    public OrderDTO() {}

    public OrderDTO(Long customerId, Long salespersonId, List<OrderDetailsDTO> orderDetails) {
        this.customerId = customerId;
        this.salespersonId = salespersonId;
        this.orderDetails = orderDetails;
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
}
