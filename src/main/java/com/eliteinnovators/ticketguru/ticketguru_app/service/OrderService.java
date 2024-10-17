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

        List<OrderDetails> orderDetailsList = order.getOrderDetails();
        List<OrderDetailsDTO> orderDetailsDTOs = orderMapper.toOrderDetailsDTOs(orderDetailsList);

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