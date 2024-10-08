package com.eliteinnovators.ticketguru.ticketguru_app.service;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.*;
import com.eliteinnovators.ticketguru.ticketguru_app.exception.CustomerNotFoundException;
import com.eliteinnovators.ticketguru.ticketguru_app.exception.SalespersonNotFoundException;
import com.eliteinnovators.ticketguru.ticketguru_app.mapper.OrderMapper;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.CustomerRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.SalespersonRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.web.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SalespersonRepository SalespersonRepository;

    public OrderDTO convertToOrderDTO(Order order) {
        OrderDTO orderDTO = orderMapper.toOrderDTO(order);
        if(order.getCustomer() != null) {
            orderDTO.setCustomerFirstName(order.getCustomer().getFirstName());
            orderDTO.setCustomerLastName(order.getCustomer().getLastName());
        }
        if (order.getSalesperson() != null) {
            orderDTO.setSalespersonFirstName(order.getSalesperson().getFirstName());
            orderDTO.setSalespersonLastName(order.getSalesperson().getLastName());
        }

        // OrderDetailsDTO:n täyttö
        List<OrderDetailsDTO> orderDetailsDTOs = new ArrayList<>();
        for (OrderDetails orderDetails : order.getOrderDetails()) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
            if (orderDetails.getTicket() != null) {
                orderDetailsDTO.setTicketId(orderDetails.getTicket().getId());
            }
            orderDetailsDTO.setQuantity(orderDetails.getQuantity());
            orderDetailsDTO.setUnitPrice(orderDetails.getUnitPrice());
            orderDetailsDTOs.add(orderDetailsDTO);
        }

        orderDTO.setOrderDetails(orderDetailsDTOs);
        return orderDTO;
    }

    public Order convertToOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toOrder(orderDTO);

        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        order.setCustomer(customer);

        Salesperson salesperson = SalespersonRepository.findById(orderDTO.getSalespersonId())
                .orElseThrow(() -> new SalespersonNotFoundException("Salesperson not found"));
        order.setSalesperson(salesperson);

        return order;
    }
}