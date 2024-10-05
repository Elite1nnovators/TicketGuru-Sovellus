package com.eliteinnovators.ticketguru.ticketguru_app.web;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    // MYYNTITAPAHTUMIEN REST -ENDPOINTIT
    /*@GetMapping("/orders")
    public Iterable<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @GetMapping("/orders/{orderId}")
    Order getOrderById(@PathVariable Long orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }

     kesken
    @PostMapping("/orders")
    public Order newOrder(@RequestBody Order newOrder) {
        return orderRepo.save(newOrder);
    }

    //kesken
    @PutMapping("orders/{orderId}")
    Order editOrder(@RequestBody Order editedOrder, @PathVariable Long OrderId) {
        editedOrder.setOrderId(orderId);
        return orderRepo.save(editedOrder);
    }

    //kesken
    @DeleteMapping("orders/{orderId}")
    public Iterable<Order> deleteOrder(@PathVariable("orderId") Long orderId) {
        orderRepo.deleteById(orderId);
        return orderRepo.findAll();
    } */
}
