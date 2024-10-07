package com.eliteinnovators.ticketguru.ticketguru_app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;
import com.eliteinnovators.ticketguru.ticketguru_app.domain.OrderDetails;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.OrderDetailsRepository;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.OrderRepository;

@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    OrderDetailsRepository orderDetailsRepo;

    // ORDER REST -ENDPOINTIT
    @GetMapping("/orders")
    public Iterable<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @GetMapping("/orders/{orderId}")
    Order getOrderById(@PathVariable Long orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }

    @PostMapping("/orders")
    public Order newOrder(@RequestBody Order newOrder) {
        return orderRepo.save(newOrder);
    }

    @PutMapping("orders/{orderId}")
    Order editOrder(@RequestBody Order editedOrder, @PathVariable Long orderId) {
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
