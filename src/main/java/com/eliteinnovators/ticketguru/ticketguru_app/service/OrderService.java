package com.eliteinnovators.ticketguru.ticketguru_app.service;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.*;
import com.eliteinnovators.ticketguru.ticketguru_app.exception.*;
import com.eliteinnovators.ticketguru.ticketguru_app.mapper.*;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.*;
import com.eliteinnovators.ticketguru.ticketguru_app.web.*;

import jakarta.transaction.Transactional;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SalespersonRepository salespersonRepository;

    public OrderDTO convertToOrderDTO(Order order) {
        OrderDTO orderDTO = orderMapper.toOrderDTO(order);

        List<OrderDetails> orderDetailsList = order.getOrderDetails();
        List<OrderDetailsDTO> orderDetailsDTOs = orderMapper.toOrderDetailsDTOs(orderDetailsList);

        orderDTO.setOrderDetails(orderDetailsDTOs);
        return orderDTO;
    }

    public Order convertToOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toOrder(orderDTO, customerRepository, salespersonRepository);

        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + orderDTO.getCustomerId() + " not found"));
        order.setCustomer(customer);

        Salesperson salesperson = salespersonRepository.findById(orderDTO.getSalespersonId())
                .orElseThrow(() -> new SalespersonNotFoundException("Salesperson with ID " + orderDTO.getSalespersonId() + " not found"));
        order.setSalesperson(salesperson);

        return order;
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        
        for (Order order : orders) {
            OrderDTO orderDTO = convertToOrderDTO(order);
            orderDTOs.add(orderDTO);
        }
        return orderDTOs;
    }

    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found"));
        
        return convertToOrderDTO(order);
    }

    @Transactional
    public Order newOrder(OrderDTO newOrderDTO) {
        Order newOrder = orderMapper.toOrder(newOrderDTO, customerRepository, salespersonRepository);
        newOrder.setOrderDate(new Date());

        List<OrderDetails> orderDetailsList = orderMapper.toOrderDetailsList(newOrderDTO.getOrderDetails(), ticketRepository);
        newOrder.setOrderDetails(orderDetailsList);

        return orderRepository.save(newOrder);
    }

    @Transactional
    public Order editOrder(OrderDTO editedOrderDTO, Long orderId) {
        Order existingOrder = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found")); 
        if(!orderRepository.findById(orderId).isPresent()) {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found");
        }

        Order updatedOrder = orderMapper.toOrder(editedOrderDTO, customerRepository, salespersonRepository);
        updatedOrder.setOrderDate(existingOrder.getOrderDate());

        List<OrderDetails> updatedOrderDetailsList = orderMapper.toOrderDetailsList(editedOrderDTO.getOrderDetails(), ticketRepository);
        updatedOrder.setOrderDetails(updatedOrderDetailsList);

        return orderRepository.save(updatedOrder);
    }
    @Transactional
    public Order patchOrder(OrderDTO patchOrderDTO, Long orderId) {
        Order existingOrder = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found"));

        applyNonNullFieldsToOrder(patchOrderDTO, existingOrder);

        Order updatedOrder = orderRepository.save(existingOrder);
        return updatedOrder;
    }
    //apumetodi luettavuuden vuoksi
    private void applyNonNullFieldsToOrder(OrderDTO patchOrderDTO, Order existingOrder) {
        if (patchOrderDTO.getCustomerId() != null) {
            Customer customer = customerRepository.findById(patchOrderDTO.getCustomerId())
                    .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + patchOrderDTO.getCustomerId() + " not found"));
            existingOrder.setCustomer(customer);
        }
        if (patchOrderDTO.getSalespersonId() != null) {
            Salesperson salesperson = salespersonRepository.findById(patchOrderDTO.getSalespersonId())
                    .orElseThrow(() -> new SalespersonNotFoundException("Salesperson with ID " + patchOrderDTO.getSalespersonId() + " not found"));
            existingOrder.setSalesperson(salesperson);
        }
        if (patchOrderDTO.getOrderDetails() != null) {
            List<OrderDetails> updatedOrderDetails = orderMapper.toOrderDetailsList(patchOrderDTO.getOrderDetails(), ticketRepository);
            existingOrder.setOrderDetails(updatedOrderDetails);
        }
    }
}