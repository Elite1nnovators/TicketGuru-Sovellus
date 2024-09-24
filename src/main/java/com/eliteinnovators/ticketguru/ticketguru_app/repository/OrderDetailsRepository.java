package com.eliteinnovators.ticketguru.ticketguru_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.OrderDetails;


public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long>{

    OrderDetails findByOrderDetailId(Long orderDetailId);

}
