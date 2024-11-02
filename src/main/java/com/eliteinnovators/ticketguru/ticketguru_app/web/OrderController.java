package com.eliteinnovators.ticketguru.ticketguru_app.web;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.*;
import com.eliteinnovators.ticketguru.ticketguru_app.service.*;

import jakarta.validation.Valid;

import com.eliteinnovators.ticketguru.ticketguru_app.mapper.OrderMapper;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.*;



@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRepository orderRepository;

    // ORDER REST -ENDPOINTIT
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@Valid @PathVariable Long orderId) {
        OrderDTO orderDTO = orderService.getOrderById(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }
    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> newOrder(@Valid @RequestBody OrderDTO newOrderDTO) {
        OrderDTO createdOrderDTO = orderService.newOrder(newOrderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderDTO);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<OrderDTO> editOrder(@RequestBody OrderDTO editedOrderDTO, @PathVariable Long orderId) {
        Order editedOrder = orderService.editOrder(editedOrderDTO, orderId);
        OrderDTO updatedOrderDTO = orderMapper.toOrderDTO(editedOrder);
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrderDTO);
    }

    @PatchMapping("/orders/{orderId}")
    public ResponseEntity<OrderDTO> patchOrder(@RequestBody OrderDTO patchOrderDTO, @PathVariable Long orderId) {
        Order patchedOrder = orderService.patchOrder(patchOrderDTO, orderId);
        OrderDTO updatedOrderDTO = orderMapper.toOrderDTO(patchedOrder);
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrderDTO);
    }

    @DeleteMapping("orders/{orderId}")
    public ResponseEntity<Object> deleteOrder(@PathVariable("orderId") Long orderId) {
        orderRepository.deleteById(orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
