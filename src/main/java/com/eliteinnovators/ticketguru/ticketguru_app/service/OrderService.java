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
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SalespersonRepository salespersonRepository;

    @Autowired
    private EventTicketTypeRepository eventTicketTypeRepository;

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOs = orderMapper.toOrderDTOs(orders);
        return orderDTOs;
    }

    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found"));
        OrderDTO orderDTO = orderMapper.toOrderDTO(order);
        return orderDTO;
    }

    @Transactional
    public OrderDTO newOrder(OrderDTO newOrderDTO) {
        Order newOrder = orderMapper.toOrder(newOrderDTO, customerRepository, salespersonRepository, eventTicketTypeRepository);
        newOrder.setOrderDate(new Date());

        List<Ticket> tickets = new ArrayList<>();

        // Päivitetään OrderDetails ja EventTicketType uutta tilausta tehdessä
        for(OrderDetails orderDetails : newOrder.getOrderDetails()) {
            EventTicketType eventTicketType = orderDetails.getEventTicketType();
            if(eventTicketType.getTicketsInStock() < orderDetails.getQuantity()) {
                throw new InsufficientTicketsException("Not enough tickets in stock");
            }
            orderDetails.setUnitPrice(eventTicketType.getPrice());
            eventTicketType.setTicketsInStock(eventTicketType.getTicketsInStock() - orderDetails.getQuantity());
            eventTicketTypeRepository.save(eventTicketType);

            //Ticketin luonti jokaista tämän orderDetailin sisältämän eventTicketTypen ostomäärää kohden
            for(int i = 0; i < orderDetails.getQuantity(); i++) {
                Ticket ticket = new Ticket();
                ticket.setEventTicketType(eventTicketType);
                ticket.setTicketCode(generateUniqueTicketCode());
                ticket.setValid(true);
                ticket.setOrder(newOrder);
                tickets.add(ticket);
            }
        }
        newOrder.setTickets(tickets);
        Order savedOrder = orderRepository.save(newOrder);
        return orderMapper.toOrderDTO(savedOrder);
    }

    @Transactional
    public Order editOrder(OrderDTO editedOrderDTO, Long orderId) {
        Order existingOrder = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found")); 
        if(!orderRepository.findById(orderId).isPresent()) {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found");
        }

        Order updatedOrder = orderMapper.toOrder(editedOrderDTO, customerRepository, salespersonRepository, eventTicketTypeRepository);
        updatedOrder.setOrderDate(existingOrder.getOrderDate());

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

    //apumetodi patchOrderille luettavuuden vuoksi
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
        //TODO onko vielä muuta jota voisi PATCHilla muuttaa?
    }

    // uniikin ticketCoden luonti (128 bit value)
    private String generateUniqueTicketCode() {
        return UUID.randomUUID().toString();
    }
}