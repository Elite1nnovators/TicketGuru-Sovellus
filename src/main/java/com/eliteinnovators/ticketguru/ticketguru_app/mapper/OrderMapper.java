package com.eliteinnovators.ticketguru.ticketguru_app.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.Order;
import com.eliteinnovators.ticketguru.ticketguru_app.web.OrderDTO;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "customer.customerId", target = "customerId")
    @Mapping(source = "salesperson.salespersonId", target = "salespersonId")
    OrderDTO toOrderDTO(Order order);

    @Mapping(target = "customer.customerId", source = "customerId")
    @Mapping(target = "salesperson.salespersonId", source = "salespersonId")
    Order toOrder(OrderDTO orderDTO);

    List<OrderDTO> toOrderDTOs(List<Order> orders);
}
