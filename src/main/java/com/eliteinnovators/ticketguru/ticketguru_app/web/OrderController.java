package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Customer;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.OrderDetails;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Salesperson;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.Ticket;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.OrderDetailsRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.OrderRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.CustomerRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.SalespersonRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.TicketRepository;



@RestController
public class OrderController {

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
    public Iterable<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @GetMapping("/orders/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }

    @PostMapping("/orders")
    public Order newOrder(@RequestBody OrderDTO newOrderDTO) {
    Customer customer = customerRepo.findById(newOrderDTO.getCustomerId())
        .orElseThrow(() -> new RuntimeException("Customer not found"));

    Salesperson salesperson = salespersonRepo.findById(newOrderDTO.getSalespersonId())
        .orElseThrow(() -> new RuntimeException("Salesperson not found"));

    Order newOrder = new Order();
    newOrder.setCustomer(customer);
    newOrder.setSalesperson(salesperson);
    newOrder.setOrderDate(new Date());

    List<OrderDetails> orderDetailsList = new ArrayList<>();
    for (OrderDetailsDTO detailsDTO : newOrderDTO.getOrderDetails()) {
        Ticket ticket = ticketRepo.findById(detailsDTO.getTicketId())
            .orElseThrow(() -> new RuntimeException("Ticket not found")); // Ensure ticket exists
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


    @PutMapping("orders/{orderId}")
    public Order editOrder(@RequestBody Order editedOrder, @PathVariable Long orderId) {
        editedOrder.setOrderId(orderId);
        return orderRepo.save(editedOrder);
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

    @GetMapping("/orderdetails/{orderdetailsId}")
    OrderDetails getOrderDetailsById(@PathVariable Long orderDetailsId) {
        return orderDetailsRepo.findById(orderDetailsId).orElse(null);
    }

    // kesken
    /*  @PostMapping("/orderdetails")
    public Order newOrderDetails(@RequestBody Order newOrderDetails) {
        return orderDetailsRepo.save(newOrderDetails);
    }*/
    @PutMapping("orderdetails/{orderdetailsId}")
    OrderDetails editOrderDetails(@RequestBody OrderDetails editedOrderDetails, @PathVariable Long orderDetailsId) {
        editedOrderDetails.setOrderDetailId(orderDetailsId);
        return orderDetailsRepo.save(editedOrderDetails);
    }

    @DeleteMapping("orderDetails/{orderDetailsId}")
    public Iterable<OrderDetails> deleteOrderDetails(@PathVariable("orderDetailsId") Long orderDetailsId) {
        orderDetailsRepo.deleteById(orderDetailsId);
        return orderDetailsRepo.findAll();
    }
}
