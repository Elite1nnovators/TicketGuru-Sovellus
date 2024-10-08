package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Customer;
import com.eliteinnovators.ticketguru.ticketguru_app.service.OrderService;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.OrderDetails;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Salesperson;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;
import com.eliteinnovators.ticketguru.ticketguru_app.exception.CustomerNotFoundException;
import com.eliteinnovators.ticketguru.ticketguru_app.exception.SalespersonNotFoundException;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.OrderDetailsRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.OrderRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.CustomerRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.SalespersonRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketRepository;



@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    OrderDetailsRepository orderDetailsRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    SalespersonRepository salespersonRepo;

    @Autowired
    TicketRepository ticketRepo;


    // ORDER REST -ENDPOINTIT
    @GetMapping("/orders")
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepo.findAll();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        
        for (Order order : orders) {
            OrderDTO orderDTO = orderService.convertToOrderDTO(order);
            orderDTOs.add(orderDTO);
        }
        
        return orderDTOs;
    }

    @GetMapping("/orders/{orderId}")
    public OrderDTO getOrderById(@PathVariable Long orderId) {
        Order order = orderRepo.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        
        return orderService.convertToOrderDTO(order);
    }
    @PostMapping("/orders")
    public Order newOrder(@RequestBody OrderDTO newOrderDTO) {
        if (newOrderDTO.getCustomerId() == null) {
            throw new IllegalArgumentException("Customer ID must not be null");
        }

        if (newOrderDTO.getSalespersonId() == null) {
            throw new IllegalArgumentException("Salesperson ID must not be null");
        }

        Customer customer = customerRepo.findById(newOrderDTO.getCustomerId())
            .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + newOrderDTO.getCustomerId() + " not found"));

        Salesperson salesperson = salespersonRepo.findById(newOrderDTO.getSalespersonId())
            .orElseThrow(() -> new SalespersonNotFoundException("Salesperson with ID " + newOrderDTO.getSalespersonId() + " not found"));

        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        newOrder.setSalesperson(salesperson);
        newOrder.setOrderDate(new Date());

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (OrderDetailsDTO detailsDTO : newOrderDTO.getOrderDetails()) {
            if (detailsDTO.getTicketId() == null) {
                throw new IllegalArgumentException("Ticket ID must not be null in OrderDetails");
            }
            Ticket ticket = ticketRepo.findById(detailsDTO.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket with ID " + detailsDTO.getTicketId() + " not found"));
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(newOrder);
            orderDetails.setTicket(ticket);
            orderDetails.setQuantity(detailsDTO.getQuantity());
            orderDetails.setUnitPrice(detailsDTO.getUnitPrice());
            orderDetailsList.add(orderDetails);
        }

        newOrder.setOrderDetails(orderDetailsList);

        return orderRepo.save(newOrder);
    }


    @PutMapping("/orders/{orderId}")
    public Order editOrder(@RequestBody OrderDTO editedOrderDTO, @PathVariable Long orderId) {
        Order existingOrder = orderRepo.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        Customer customer = customerRepo.findById(editedOrderDTO.getCustomerId())
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        Salesperson salesperson = salespersonRepo.findById(editedOrderDTO.getSalespersonId())
            .orElseThrow(() -> new RuntimeException("Salesperson not found"));

        existingOrder.setCustomer(customer);
        existingOrder.setSalesperson(salesperson);
        existingOrder.setOrderDate(editedOrderDTO.getOrderDate());

        List<OrderDetails> updatedOrderDetailsList = new ArrayList<>();
        for (OrderDetailsDTO detailsDTO : editedOrderDTO.getOrderDetails()) {
            Ticket ticket = ticketRepo.findById(detailsDTO.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket with ID " + detailsDTO.getTicketId() + " not found"));

            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(existingOrder);
            orderDetails.setTicket(ticket);
            orderDetails.setQuantity(detailsDTO.getQuantity());
            orderDetails.setUnitPrice(detailsDTO.getUnitPrice());

            updatedOrderDetailsList.add(orderDetails);
        }

        existingOrder.setOrderDetails(updatedOrderDetailsList);

        return orderRepo.save(existingOrder);
    }

    @PatchMapping("/orders/{orderId}")
    public OrderDTO patchOrder(@RequestBody OrderDTO patchOrderDTO, @PathVariable Long orderId) {

        Order existingOrder = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (patchOrderDTO.getCustomerId() != null) {
            Customer customer = customerRepo.findById(patchOrderDTO.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            if (patchOrderDTO.getCustomerFirstName() != null) {
                customer.setFirstName(patchOrderDTO.getCustomerFirstName());
            }
            if (patchOrderDTO.getCustomerLastName() != null) {
                customer.setLastName(patchOrderDTO.getCustomerLastName());
            }

            existingOrder.setCustomer(customer);
        }

        if (patchOrderDTO.getSalespersonId() != null) {
            Salesperson salesperson = salespersonRepo.findById(patchOrderDTO.getSalespersonId())
                    .orElseThrow(() -> new RuntimeException("Salesperson not found"));

            if (patchOrderDTO.getSalespersonFirstName() != null) {
                salesperson.setFirstName(patchOrderDTO.getSalespersonFirstName());
            }
            if (patchOrderDTO.getSalespersonLastName() != null) {
                salesperson.setLastName(patchOrderDTO.getSalespersonLastName());
            }

            existingOrder.setSalesperson(salesperson);
        }

        if (patchOrderDTO.getOrderDetails() != null) {
            List<OrderDetails> existingOrderDetails = existingOrder.getOrderDetails();

            for (OrderDetailsDTO detailsDTO : patchOrderDTO.getOrderDetails()) {
                boolean updated = false;

                for (OrderDetails existingDetails : existingOrderDetails) {
                    if (existingDetails.getTicket().getId().equals(detailsDTO.getTicketId())) {

                        existingDetails.setQuantity(detailsDTO.getQuantity());
                        existingDetails.setUnitPrice(detailsDTO.getUnitPrice());
                        updated = true;
                        break;
                    }
                }

                if (!updated) {
                    Ticket ticket = ticketRepo.findById(detailsDTO.getTicketId())
                            .orElseThrow(() -> new RuntimeException("Ticket not found"));

                    OrderDetails newOrderDetails = new OrderDetails();
                    newOrderDetails.setTicket(ticket);
                    newOrderDetails.setQuantity(detailsDTO.getQuantity());
                    newOrderDetails.setUnitPrice(detailsDTO.getUnitPrice());
                    newOrderDetails.setOrder(existingOrder);

                    existingOrderDetails.add(newOrderDetails);
                }
            }
        }

        Order updatedOrder = orderRepo.save(existingOrder);

        return orderService.convertToOrderDTO(updatedOrder);
    }

    @DeleteMapping("orders/{orderId}")
    public Iterable<Order> deleteOrder(@PathVariable("orderId") Long orderId) {
        orderRepo.deleteById(orderId);
        return orderRepo.findAll();
    }

    // ORDERDETAILS REST -ENDPOINTIT
    @GetMapping("/orderdetails")
    public Iterable<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepo.findAll();
    }

    @GetMapping("/orderdetails/{orderDetailsId}")
    OrderDetails getOrderDetailsById(@PathVariable Long orderDetailsId) {
        return orderDetailsRepo.findById(orderDetailsId).orElse(null);
    }

    @PutMapping("orderdetails/{orderDetailsId}")
    OrderDetails editOrderDetails(@RequestBody OrderDetails editedOrderDetails, @PathVariable Long orderDetailsId) {
        editedOrderDetails.setOrderDetailId(orderDetailsId);
        return orderDetailsRepo.save(editedOrderDetails);
    }

    @DeleteMapping("orderdetails/{orderDetailsId}")
    public Iterable<OrderDetails> deleteOrderDetails(@PathVariable("orderDetailsId") Long orderDetailsId) {
        orderDetailsRepo.deleteById(orderDetailsId);
        return orderDetailsRepo.findAll();
    }
}
