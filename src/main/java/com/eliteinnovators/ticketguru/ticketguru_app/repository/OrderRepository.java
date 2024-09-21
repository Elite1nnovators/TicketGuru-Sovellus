package com.eliteinnovators.ticketguru.ticketguru_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
