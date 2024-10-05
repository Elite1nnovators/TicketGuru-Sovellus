package com.eliteinnovators.ticketguru.ticketguru_app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.OrderRepository;

@RestController
public class OrderController {

     @Autowired
    OrderRepository orderRepo;


    // MYYNTITAPAHTUMIEN REST -ENDPOINTIT
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

    //kesken
    @PutMapping("orders/{orderId}")
    Order editOrder(@RequestBody Order editedOrder, @PathVariable Long orderId) {
        editedOrder.setOrderId(orderId);
        return orderRepo.save(editedOrder);
    }

    //kesken
    @DeleteMapping("orders/{orderId}")
    public Iterable<Order> deleteOrder(@PathVariable("orderId") Long orderId) {
        orderRepo.deleteById(orderId);
        return orderRepo.findAll();
    }
}
