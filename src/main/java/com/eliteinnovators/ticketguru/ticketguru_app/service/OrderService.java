package com.eliteinnovators.ticketguru.ticketguru_app.service;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.*;
import com.eliteinnovators.ticketguru.ticketguru_app.web.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    public OrderDTO convertToOrderDTO(Order order) {
        Customer customer = order.getCustomer();
        Salesperson salesperson = order.getSalesperson();
        
        CustomerDTO customerDTO = new CustomerDTO(
            customer.getCustomerId(),
            customer.getFirstName(),
            customer.getLastName(),
            customer.getEmail(),
            customer.getPhone(),
            customer.getCity()
        );
        
        SalespersonDTO salespersonDTO = new SalespersonDTO(
            salesperson.getSalespersonId(),
            salesperson.getFirstName(),
            salesperson.getLastName(),
            salesperson.isAdmin()
        );
        
        List<OrderDetailsDTO> orderDetailsDTOs = new ArrayList<>();
        for (OrderDetails orderDetails : order.getOrderDetails()) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                orderDetails.getTicket().getId(), 
                orderDetails.getQuantity(),
                orderDetails.getUnitPrice()
            );
            orderDetailsDTOs.add(orderDetailsDTO);
        }

        return new OrderDTO(orderDetailsDTOs, order.getOrderId(), customer.getCustomerId(), salesperson.getSalespersonId(), order.getOrderDate(), customerDTO, salespersonDTO);
    }
}